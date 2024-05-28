package org.serratec.ecommerce.services;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.serratec.ecommerce.configurations.EmailConfig;
import org.serratec.ecommerce.dtos.ClienteDTO;
import org.serratec.ecommerce.dtos.ClienteInserirDTO;
import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Endereco;
import org.serratec.ecommerce.exceptions.ConfirmaSenhaException;
import org.serratec.ecommerce.exceptions.EmailException;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private EnderecoService enderecoService;
    
    @Autowired
    private EmailConfig emailConfig;

    @Override
    @Transactional
	public ClienteDTO inserir(ClienteInserirDTO clienteInserirDTO){
		if (!clienteInserirDTO.getSenha().equalsIgnoreCase(clienteInserirDTO.getConfirmaSenha())) {
			throw new ConfirmaSenhaException("Senha e Confirma Senha não são iguais");
		}
		Cliente usuarioBd = clienteRepository.findByEmail(clienteInserirDTO.getEmail());
		if (usuarioBd != null) {
			throw new EmailException("Email ja existente");
		}
		
		Cliente cliente = new Cliente();
		cliente.setNome(clienteInserirDTO.getNome());
		cliente.setEmail(clienteInserirDTO.getEmail());
		cliente.setSenha(encoder.encode(clienteInserirDTO.getSenha()));
			
        Endereco endereco = enderecoService.buscar(cliente.getCep());
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);
        
        emailConfig.sendEmail(cliente.getEmail(), "Conta criada com sucesso!", cliente.toString());
        
        ClienteDTO clienteDTO = new ClienteDTO(cliente);
        clienteRepository.save(cliente);
        return clienteDTO;
    }

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

     // Obtém o endereço antigo associado ao cliente
        Endereco enderecoAntigo = cliente.getEndereco();

        // Verifica se o CEP foi atualizado
        if (!cliente.getCep().equals(enderecoAntigo.getCep())) {
            // Remove a associação do cliente ao endereço antigo
            enderecoAntigo.setCliente(null);

            // Busca o novo endereço com base no CEP fornecido
            Endereco novoEndereco = enderecoService.buscar(clienteAtualizado.getCep());
            // Associa o novo endereço ao cliente
            novoEndereco.setCliente(cliente);
            // Define o novo endereço para o cliente
            cliente.setEndereco(novoEndereco);
        };
        
        emailConfig.sendEmail(cliente.getEmail(), "Cadastro do Cliente atualizado", cliente.toString());

        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void deletarCliente(Long id) {
        Cliente cliente = buscarClientePorIdEntity(id);
        clienteRepository.delete(cliente);
    }

    @Transactional
    public ClienteDTO inserirComFoto(Cliente cliente, MultipartFile file) throws IOException {
    	
    	cliente.setSenha(encoder.encode(cliente.getSenha()));
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
}
