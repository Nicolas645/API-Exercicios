package org.serratec.ecommerce.repositories;

import java.util.Optional;

import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
	public Optional<Foto> findByCliente(Cliente cliente);
	void deleteByClienteId(Long clienteId);
}
