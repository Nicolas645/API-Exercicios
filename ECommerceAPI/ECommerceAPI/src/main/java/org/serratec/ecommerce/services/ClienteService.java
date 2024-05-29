package org.serratec.ecommerce.services;

import java.io.IOException;
import java.util.List;

import org.serratec.ecommerce.dtos.ClienteDTO;
import org.serratec.ecommerce.entities.Cliente;
import org.springframework.web.multipart.MultipartFile;

public interface ClienteService {

    List<ClienteDTO> listarClientes();

    ClienteDTO buscarClientePorId(Long id);

    Cliente atualizarCliente(Long id, Cliente clienteAtualizado);

    void deletarCliente(Long id);

    ClienteDTO inserirComFoto(Cliente cliente, MultipartFile file) throws IOException;

    // Novo método para validar número de telefone
    void validarNumeroDeTelefone(String phoneNumber, String countryCode);
}
