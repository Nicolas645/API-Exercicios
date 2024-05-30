package org.serratec.ecommerce.repositories;

import java.util.Optional;

import org.serratec.ecommerce.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    Optional<Foto> findByProdutoId(Long produtoId);
    void deleteByProdutoId(Long produtoId);
    Long countByProdutoId(Long produtoId);
}

