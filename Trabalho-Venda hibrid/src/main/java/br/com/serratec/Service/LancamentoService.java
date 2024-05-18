package br.com.serratec.Service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.Entity.LancamentoVendas;
import br.com.serratec.dto.LancamentoVendasMostrarDTO;
import br.com.serratec.repository.LancamentoRepository;
import jakarta.validation.constraints.NotNull;

@Service
public class LancamentoService {

	@Autowired
	private final LancamentoRepository lancamentoRepository;

	public LancamentoService(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}

	public List<LancamentoVendasMostrarDTO> listar() {

		List<LancamentoVendas> lancamentos = lancamentoRepository.findAll();

		List<LancamentoVendasMostrarDTO> listaDTO = new ArrayList<>();

		for (LancamentoVendas lancamento : lancamentos) {
			listaDTO.add(new LancamentoVendasMostrarDTO(lancamento.getDataVenda(), lancamento.getValorVenda(),
					lancamento.getVendedor().getNome()));
		}
		return listaDTO;
	}

	public LancamentoVendas listarPorId(@NotNull Long id) {
		try {
			return lancamentoRepository.findById(id).orElseThrow();
		} catch (Exception e) {

			throw new ServiceException("Erro ao buscar lançamento por ID: " + id, e);
		}
	}

	public Page<LancamentoVendas> listarPorPagina(Pageable pageable) {
		try {
			return lancamentoRepository.findAll(pageable);
		} catch (Exception e) {

			throw new ServiceException("Erro ao buscar lançamentos paginados", e);
		}
	}

	public LancamentoVendas inserir(LancamentoVendas lancamento) {
		try {
			return lancamentoRepository.save(lancamento);
		} catch (Exception e) {

			throw new ServiceException("Erro ao inserir lançamento", e);
		}
	}

	public LancamentoVendas atualizar(Long id, LancamentoVendas lancamentoAtualizado) {
		try {
			LancamentoVendas lancamento = listarPorId(id);

			lancamento.setDataVenda(lancamentoAtualizado.getDataVenda());
			lancamento.setValorVenda(lancamentoAtualizado.getValorVenda());

			return lancamentoRepository.save(lancamento);
		} catch (Exception e) {

			throw new ServiceException("Erro ao atualizar lançamento com ID: " + id, e);
		}
	}

	public void deletar(Long id) {
		try {
			LancamentoVendas lancamento = listarPorId(id);
			lancamentoRepository.delete(lancamento);
		} catch (Exception e) {

			throw new ServiceException("Erro ao deletar lançamento com ID: " + id, e);
		}
	}
}