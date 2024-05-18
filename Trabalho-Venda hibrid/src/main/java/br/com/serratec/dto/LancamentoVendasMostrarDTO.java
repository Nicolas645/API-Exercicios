package br.com.serratec.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoVendasMostrarDTO {
	
	private LocalDate dataVenda;
    private BigDecimal valorVenda;
    private String nomeVendedor;
    
	public LancamentoVendasMostrarDTO(LocalDate dataVenda, BigDecimal valorVenda, String nomeVendedor) {
		this.dataVenda = dataVenda;
		this.valorVenda = valorVenda;
		this.nomeVendedor = nomeVendedor;
	}
	
	public LocalDate getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public BigDecimal getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public String getNomeVendedor() {
		return nomeVendedor;
	}
	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}
    
    
}