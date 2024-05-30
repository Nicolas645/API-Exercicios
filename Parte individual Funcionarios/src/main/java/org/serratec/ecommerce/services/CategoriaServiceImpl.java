package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.Categoria;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria criarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isEmpty()) {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + id);
        }
        return categoria.get();
    }

    @Override
    public Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = buscarCategoriaPorId(id);
        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setDescricao(categoriaAtualizada.getDescricao());
       
        
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deletarCategoria(Long id) {
        Categoria categoria = buscarCategoriaPorId(id);
        categoriaRepository.delete(categoria);
    }
}
