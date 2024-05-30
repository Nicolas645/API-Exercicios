package org.serratec.ecommerce.services;

import java.io.IOException;

import org.serratec.ecommerce.entities.Foto;
import org.serratec.ecommerce.entities.Produto;
import org.springframework.web.multipart.MultipartFile;

public interface FotoService {

	public void inserir(Produto produto, MultipartFile file) throws IOException;
	
	public Foto buscarPorIdProduto(Long id);
	
	public void deletarPorIdProduto(Long id);
	
	public void atualizar(Produto produto, MultipartFile file) throws IOException;
}
