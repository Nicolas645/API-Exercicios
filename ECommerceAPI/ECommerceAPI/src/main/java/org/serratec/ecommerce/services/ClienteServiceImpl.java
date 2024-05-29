package org.serratec.ecommerce.services;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.configurations.EmailConfig;
import org.serratec.ecommerce.dtos.ClienteDTO;
import org.serratec.ecommerce.dtos.NumverifyResponseDTO;
import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Endereco;
import org.serratec.ecommerce.exceptions.CPFViolationException;
import org.serratec.ecommerce.exceptions.EmailViolationException;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private NumverifyService numverifyService;

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::adicionarImagemUri)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO buscarClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        return adicionarImagemUri(cliente.get());
    }

    private Cliente buscarClientePorIdEntity(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        return cliente.get();
    }

    @Override
    @Transactional
    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        Cliente cliente = buscarClientePorIdEntity(id);
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setSenha(clienteAtualizado.getSenha());
        cliente.setCep(clienteAtualizado.getCep());

        // Valida o telefone
        validarNumeroDeTelefone(clienteAtualizado.getTelefone(), "BR");

        // Busca o cliente atual com o endereço associado
        Cliente clienteExistente = buscarClientePorIdEntity(id);

        // Busca o novo endereço com base no CEP fornecido
        Endereco novoEndereco = enderecoService.buscar(clienteAtualizado.getCep());

        // Verifica se o novo endereço é válido
        if (novoEndereco != null) {
            // Verifica se o cliente atual possui um endereço associado
            if (clienteExistente.getEndereco() != null) {
                // Se sim, atualiza os dados do endereço existente com os dados do novo endereço
                Endereco enderecoExistente = clienteExistente.getEndereco();
                enderecoExistente.setCep(novoEndereco.getCep());
                enderecoExistente.setLogradouro(novoEndereco.getLogradouro());
                enderecoExistente.setBairro(novoEndereco.getBairro());
                enderecoExistente.setLocalidade(novoEndereco.getLocalidade());
                enderecoExistente.setUf(novoEndereco.getUf());

                // Associa o endereço atualizado ao cliente
                enderecoExistente.setCliente(clienteExistente);
                clienteExistente.setEndereco(enderecoExistente);
            } else {
                // Se não, associa o novo endereço ao cliente
                novoEndereco.setCliente(clienteExistente);
                clienteExistente.setEndereco(novoEndereco);
            }

            // Salva o cliente atualizado
            emailConfig.sendEmail(cliente.getEmail(), "Cadastro do Cliente atualizado", cliente.toString());
            return clienteRepository.save(clienteExistente);
        } else {
            throw new ResourceNotFoundException("Endereço não encontrado para o CEP fornecido: " + clienteAtualizado.getCep());
        }
    }

    @Override
    @Transactional
    public void deletarCliente(Long id) {
        Cliente cliente = buscarClientePorIdEntity(id);
        clienteRepository.delete(cliente);
    }

    @Transactional
    public ClienteDTO inserirComFoto(Cliente cliente, MultipartFile file) throws IOException {
       
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new EmailViolationException("Este e-mail já está cadastrado. Por favor, escolha outro e-mail.");
        }
        
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CPFViolationException("Este CPF já está cadastrado. Por favor, escolha outro CPF.");
        }

        // Valida o telefone
        validarNumeroDeTelefone(cliente.getTelefone(), "BR");
        
        // Salvar o cliente
        emailConfig.sendEmail(cliente.getEmail(), "Conta criada com sucesso!", cliente.toString());
        
        cliente = clienteRepository.save(cliente);
        Endereco endereco = enderecoService.buscar(cliente.getCep());
       
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);
        fotoService.inserir(cliente, file);
        return adicionarImagemUri(cliente);
    }

    private ClienteDTO adicionarImagemUri(Cliente cliente) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/clientes/{id}/foto")
                .buildAndExpand(cliente.getId())
                .toUri();
        ClienteDTO dto = new ClienteDTO();
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setCpf(cliente.getCpf());
        dto.setSenha(cliente.getSenha());
        dto.setFotourl(uri.toString());
        return dto;
    }

    @Override
    public void validarNumeroDeTelefone(String phoneNumber, String countryCode) {
        NumverifyResponseDTO response = numverifyService.validatePhoneNumber(phoneNumber, countryCode);
        if (response == null || !response.isValid()) {
            throw new IllegalArgumentException("Número de telefone inválido.");
        }
    }
}

