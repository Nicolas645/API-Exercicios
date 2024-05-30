package org.serratec.ecommerce.services;

import java.util.List;

import org.serratec.ecommerce.entities.Categoria;

public interface CategoriaService {
	Categoria criarCategoria(Categoria categoria);

	List<Categoria> listarCategorias();

	Categoria buscarCategoriaPorId(Long id);

	Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada);

	void deletarCategoria(Long id);
}
