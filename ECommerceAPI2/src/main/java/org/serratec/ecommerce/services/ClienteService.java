package org.serratec.ecommerce.services;

import java.io.IOException;
import java.util.List;

import org.serratec.ecommerce.dtos.ClienteDTO;
import org.serratec.ecommerce.entities.Cliente;

public interface ClienteService {

	List<ClienteDTO> listarClientes();

	ClienteDTO buscarClientePorId(Long id);

//	Cliente atualizarCliente(Long id, Cliente clienteAtualizado); //s

	void deletarCliente(Long id);
	
	ClienteDTO inserir(Cliente cliente) throws IOException;

	Cliente buscarClientePorIdEntity(Long id);

	ClienteDTO atualizarCliente(Long id, ClienteDTO clienteAtualizadoDTO); 
}
