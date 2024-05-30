package org.serratec.ecommerce.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "fotos")
public class Foto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "minha_sequencia", sequenceName = "minha_sequencia", allocationSize = 1)
	private Long id;
	
	@Lob
	private byte[] dados;
	
	private String tipo;
	
	private String nome;
	
	@OneToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	public Foto() {
	}

	public Foto(Long id, byte[] dados, String tipo, String nome, Produto produto, String url) {
	this.id = id;
	this.dados = dados;
	this.tipo = tipo;
	this.nome = nome;
	this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
	