package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dtos.CategoriaDTO;
import org.serratec.ecommerce.dtos.ProdutoDTO;
import org.serratec.ecommerce.entities.Foto;
import org.serratec.ecommerce.entities.Produto;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.FotoRepository;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoRepository fotoRepository;

	@Override
	public Produto criarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Override
	public List<ProdutoDTO> listarProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		return produtos.stream().map(this::adicionarImagemUri).collect(Collectors.toList());
	}

	@Override
	public Produto buscarProdutoPorId(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isEmpty()) {
			throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
		}
		return produto.get();
	}

	@Override
	public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
		Produto produto = buscarProdutoPorId(id);
		produto.setNome(produtoAtualizado.getNome());
		produto.setPreco(produtoAtualizado.getPreco());
		produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
		produto.setCategoria(produtoAtualizado.getCategoria());
		produto.setUrl(produtoAtualizado.getUrl());
		return produtoRepository.save(produto);
	}

	@Override
	public void deletarProduto(Long id) {
		Produto produto = buscarProdutoPorId(id);
		produtoRepository.delete(produto);
	}

	@Override
	public ProdutoDTO adicionarImagemUri(Produto produto) {
	    Foto foto = fotoRepository.findByProdutoId(produto.getId()).orElse(null);
	    String url = null;
	    if (foto != null && foto.getTipo() != null) {
	        url = ServletUriComponentsBuilder
	                .fromCurrentContextPath()
	                .path("/produtos/{id}/foto")
	                .buildAndExpand(produto.getId())
	                .toUri()
	                .toString();
	    }
	    CategoriaDTO categoriaDTO = new CategoriaDTO(produto.getCategoria().getNome(), produto.getCategoria().getDescricao());
	    return new ProdutoDTO(produto.getNome(), produto.getDescricao(), produto.getPreco(), categoriaDTO, url);
	}


}
