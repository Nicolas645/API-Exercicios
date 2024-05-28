package org.serratec.ecommerce.repositories;

import org.serratec.ecommerce.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findByEmail(String email);

	Cliente findByCpf(String cpf);

	boolean existsByCpf(String cpf); 
	
	UserDetails findByLogin(String Login);
}
