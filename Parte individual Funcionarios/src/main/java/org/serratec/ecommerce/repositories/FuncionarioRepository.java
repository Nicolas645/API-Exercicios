package org.serratec.ecommerce.repositories;


import java.util.Optional;

import org.serratec.ecommerce.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	Optional<Funcionario> findById(Long id);
}

