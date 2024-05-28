package org.serratec.ecommerce.services;

import java.io.IOException;
import java.util.Optional;

import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Foto;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class FotoServiceImpl implements FotoService {
	@Autowired
	private FotoRepository fotoRepository;

	@Override
	public Foto inserir(Cliente cliente, MultipartFile file) throws IOException {
		Foto foto = new Foto();
		foto.setNome(file.getName());
		foto.setTipo(file.getContentType());
		foto.setDados(file.getBytes());
		foto.setCliente(cliente);
		return fotoRepository.save(foto);
	}

	@Override
	@Transactional
	public byte[] buscarPorIdCliente(Long id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		Optional<Foto> fotoOptional = fotoRepository.findByCliente(cliente);
		if (fotoOptional.isPresent()) {
			Foto foto = fotoOptional.get();
			return foto.getDados();
		} else {

			throw new ResourceNotFoundException("Foto não encontrada para o cliente com ID: " + id);
		}
	}
}
