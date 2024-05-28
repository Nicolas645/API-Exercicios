package org.serratec.ecommerce.dtos;

import org.serratec.ecommerce.entities.Cliente;

public class ClienteDTO {

	private String nome;

	private String telefone;

	private String email;

	private String senha;

	private String cpf;
	
	private String cep;

	
	  public Cliente toEntity() {
	        Cliente cliente = new Cliente();
	        cliente.setNome(this.nome);
	        cliente.setTelefone(this.telefone);
	        cliente.setEmail(this.email);
	        cliente.setSenha(this.senha);
	        cliente.setCpf(this.cpf);
	        cliente.setCep(this.cep);
			return cliente;
		}
	
	
	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Cliente cliente) {
		super();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.senha = cliente.getSenha();
		this.cpf = cliente.getCpf();
		this.cep = cliente.getCep();
	}

	
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
