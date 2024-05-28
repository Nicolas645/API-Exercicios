package org.serratec.ecommerce.dtos;

import java.util.List;

public class PedidoDTO {

	private List<ItemPedidoDTO> itens;
	private ClienteDTO cliente;
	private String status;	
	
	public PedidoDTO(List<ItemPedidoDTO> itens, ClienteDTO cliente, String status) {
		super();
		this.itens = itens;
		this.cliente = cliente;
		this.status = status;
	}
	
	
	public List<ItemPedidoDTO> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
	public ClienteDTO getCliente() {
		return cliente;
	}
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
