package org.serratec.ecommerce.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.configurations.EmailConfig;
import org.serratec.ecommerce.dtos.ClienteDTO;
import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Endereco;
import org.serratec.ecommerce.exceptions.CPFViolationException;
import org.serratec.ecommerce.exceptions.EmailViolationException;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
    private EmailConfig emailConfig;

	@Override
	public List<ClienteDTO> listarClientes() {
	    return clienteRepository.findAll().stream()
	            .map(ClienteDTO::new)
	            .collect(Collectors.toList());
	}

	@Override
	public ClienteDTO buscarClientePorId(Long id) {
	    Optional<Cliente> clienteOptional = clienteRepository.findById(id);
	    if (clienteOptional.isPresent()) {
	        Cliente cliente = clienteOptional.get();
	        return new ClienteDTO(cliente);
	    } else {
	        throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
	    }
	}
	
	
	
	@Override
	@Transactional
	public ClienteDTO inserir(Cliente cliente) throws IOException {
	    // Verifique se o e-mail e o CPF já existem
	    if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
	        throw new EmailViolationException("Este e-mail já está cadastrado. Por favor, escolha outro e-mail.");
	    }
	    
	    if (clienteRepository.existsByCpf(cliente.getCpf())) {
	        throw new CPFViolationException("Este CPF já está cadastrado. Por favor, escolha outro CPF.");
	    }

	    // Salvar o cliente
	    cliente = clienteRepository.save(cliente);
	    
	    // Buscar o endereço pelo CEP
	    Endereco endereco = enderecoService.buscar(cliente.getCep());
	    
	    // Associar o endereço ao cliente
	    endereco.setCliente(cliente);
	    cliente.setEndereco(endereco);
	    
	    // Retornar o cliente inserido como DTO
	    return new ClienteDTO(cliente);
	}


	@Override
	@Transactional
	public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteAtualizadoDTO) {
	    Cliente cliente = buscarClientePorIdEntity(id);

	    // Atualiza os dados do cliente com base nos dados fornecidos
	    cliente.setNome(clienteAtualizadoDTO.getNome());
	    cliente.setTelefone(clienteAtualizadoDTO.getTelefone());
	    cliente.setEmail(clienteAtualizadoDTO.getEmail());
	    cliente.setCpf(clienteAtualizadoDTO.getCpf());
	    cliente.setSenha(clienteAtualizadoDTO.getSenha());
	    cliente.setCep(clienteAtualizadoDTO.getCep());

	    // Busca o cliente atual com o endereço associado
	    Cliente clienteExistente = buscarClientePorIdEntity(id);

	    // Busca o novo endereço com base no CEP fornecido
	    Endereco novoEndereco = enderecoService.buscar(clienteAtualizadoDTO.getCep());

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
	        clienteRepository.save(clienteExistente);
	        return new ClienteDTO(clienteExistente);
	    } else {
	        throw new ResourceNotFoundException("Endereço não encontrado para o CEP fornecido: " + clienteAtualizadoDTO.getCep());
	    }
	}

	
	public Cliente buscarClientePorIdEntity(Long id) {
	    Optional<Cliente> clienteOptional = clienteRepository.findById(id);
	    if (clienteOptional.isPresent()) {
	        return clienteOptional.get();
	    } else {
	        throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
	    }
	}



	@Override
	@Transactional
	public void deletarCliente(Long id) {
	    Optional<Cliente> clienteOptional = clienteRepository.findById(id);
	    if (clienteOptional.isPresent()) {
	        clienteRepository.delete(clienteOptional.get());
	    } else {
	        throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
	    }
	}

	
}