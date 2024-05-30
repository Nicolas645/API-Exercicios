package org.serratec.ecommerce.services;

import java.io.IOException;

import org.serratec.ecommerce.entities.Foto;
import org.serratec.ecommerce.entities.Produto;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.FotoRepository;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FotoServiceImpl implements FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void inserir(Produto produto, MultipartFile file) throws IOException {
		Foto foto = new Foto();
		foto.setProduto(produto);
		foto.setTipo(file.getContentType());
		foto.setNome(file.getOriginalFilename()); // Usando o nome do arquivo
		foto.setDados(file.getBytes());
		fotoRepository.save(foto);

		// Atualizar URL do produto apenas se o tipo da foto não for nulo
		if (foto.getTipo() != null) {
			Produto produtoAtualizado = produtoRepository.findById(produto.getId()).orElse(null);
			if (produtoAtualizado != null && produtoAtualizado.getUrl() == null) {
				produtoAtualizado.setUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/produtos/{id}/foto").buildAndExpand(produto.getId()).toUri().toString());
				produtoRepository.save(produtoAtualizado);
			}
		}
	}

	@Override
	public Foto buscarPorIdProduto(Long id) {
		return fotoRepository.findByProdutoId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Foto não encontrada para o produto com ID: " + id));
	}

	@Override
	public void deletarPorIdProduto(Long idProduto) {
	    Foto foto = fotoRepository.findByProdutoId(idProduto).orElse(null);
	    if (foto != null) {
	        fotoRepository.delete(foto);

	        // Atualizar URL do produto apenas se a foto excluída for a única associada ao produto
	        Long count = fotoRepository.countByProdutoId(idProduto);
	        if (count == 0) {
	            Produto produto = produtoRepository.findById(idProduto).orElse(null);
	            if (produto != null) {
	                produto.setUrl(null);
	                produtoRepository.save(produto);
	            }
	        }
	    }
	}

	@Override
	public void atualizar(Produto produto, MultipartFile file) throws IOException {
	    Foto foto = fotoRepository.findByProdutoId(produto.getId()).orElse(null);

	    if (file.isEmpty()) {
	        // Se o arquivo estiver vazio, removemos a foto e definimos a URL como nula
	        if (foto != null) {
	            fotoRepository.delete(foto);
	            produto.setUrl(null); // Definindo a URL como nula
	            produtoRepository.save(produto);
	        }
	        return;
	    }

	    if (foto == null) {
	        foto = new Foto();
	        foto.setProduto(produto);
	    }
	    foto.setTipo(file.getContentType());
	    foto.setNome(file.getOriginalFilename());
	    foto.setDados(file.getBytes());
	    fotoRepository.save(foto);

	    // Atualizar URL do produto apenas se o tipo da foto não for nulo
	    if (foto.getTipo() != null) {
	        Produto produtoAtualizado = produtoRepository.findById(produto.getId()).orElse(null);
	        if (produtoAtualizado != null) {
	            produtoAtualizado.setUrl(ServletUriComponentsBuilder
	                    .fromCurrentContextPath()
	                    .path("/produtos/{id}/foto")
	                    .buildAndExpand(produto.getId())
	                    .toUri()
	                    .toString());
	            produtoRepository.save(produtoAtualizado);
	        }
	    } else {
	        // Atualizar URL do produto para null se não houver mais foto associada ao produto
	        Long count = fotoRepository.countByProdutoId(produto.getId());
	        if (count == 0) {
	            Produto produtoSemFoto = produtoRepository.findById(produto.getId()).orElse(null);
	            if (produtoSemFoto != null) {
	                produtoSemFoto.setUrl(null);
	                produtoRepository.save(produtoSemFoto);
	            }
	        }
	    }
	}
}
