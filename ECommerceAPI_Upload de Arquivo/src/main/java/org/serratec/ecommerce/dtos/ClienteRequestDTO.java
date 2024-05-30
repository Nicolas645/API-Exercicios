package org.serratec.ecommerce.dtos;

import org.serratec.ecommerce.entities.Cliente;

public class ClienteRequestDTO {

	private String nome;
	private String email;
	private String senha;
	private String confirmaSenha;

	public ClienteRequestDTO() {
	}

	public ClienteRequestDTO(Cliente cliente) {
		nome = cliente.getNome();
		email = cliente.getEmail();
		senha = cliente.getSenha();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

}