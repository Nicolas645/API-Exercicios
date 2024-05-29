package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dtos.CategoriaDTO;
import org.serratec.ecommerce.dtos.ProdutoDTO;
import org.serratec.ecommerce.entities.Produto;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
        return produtoRepository.save(produto);
    }

    @Override
    public void deletarProduto(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }

    private ProdutoDTO convertToDto(Produto produto) {
        CategoriaDTO categoriaDTO = new CategoriaDTO(produto.getCategoria().getNome(), produto.getCategoria().getDescricao());
        return new ProdutoDTO(produto.getNome(), produto.getDescricao(), produto.getPreco(), categoriaDTO);
    }
}
