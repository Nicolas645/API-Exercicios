package org.serratec.ecommerce.services;

import java.util.List;

import org.serratec.ecommerce.dtos.ProdutoDTO;
import org.serratec.ecommerce.entities.Produto;

public interface ProdutoService {
	
	Produto criarProduto(Produto produto);

	List<ProdutoDTO> listarProdutos();

	Produto buscarProdutoPorId(Long id);

	Produto atualizarProduto(Long id, Produto produtoAtualizado);

	void deletarProduto(Long id);
}
