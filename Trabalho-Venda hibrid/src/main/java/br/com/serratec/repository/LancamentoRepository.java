package br.com.serratec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.Entity.LancamentoVendas;

public interface LancamentoRepository extends JpaRepository<LancamentoVendas, Long>{
	LancamentoVendas findByid(Long id);
}
