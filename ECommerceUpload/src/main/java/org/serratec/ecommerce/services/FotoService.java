package org.serratec.ecommerce.services;

import java.io.IOException;

import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Foto;
import org.springframework.web.multipart.MultipartFile;

public interface FotoService {

    Foto inserir(Cliente cliente, MultipartFile file) throws IOException;

    byte[] buscarPorIdCliente(Long id);

}

