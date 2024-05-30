package org.serratec.ecommerce.dtos;

public class ItemPedidoDTO {

	private ProdutoDTO produto;
	
	private int quantidade;
	

	public ItemPedidoDTO(ProdutoDTO produto, int quantidade) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public ProdutoDTO getProduto() {
		return produto;
	}

	public void setProduto(ProdutoDTO produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	

}