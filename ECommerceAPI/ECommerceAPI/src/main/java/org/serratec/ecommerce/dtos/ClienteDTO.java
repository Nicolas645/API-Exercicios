package org.serratec.ecommerce.dtos;

import org.serratec.ecommerce.entities.Cliente;

public class ClienteDTO {
	    
	private String nome;
	    
	private String telefone;
	    
	private String email;
	
	private String senha;
	
	private String fotourl;
	
	private String cpf;
	  
	
	

		public ClienteDTO() {
		super();
	}


		public ClienteDTO(Cliente cliente) {
			super();
			this.nome = cliente.getNome();
			this.telefone = cliente.getTelefone();
			this.email = cliente.getEmail();
			this.senha = cliente.getSenha();
			this.fotourl = cliente.getFotourl();
			this.cpf = cliente.getCpf();
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

		

		public String getFotourl() {
			return fotourl;
		}


		public void setFotourl(String url) {
			this.fotourl = url;
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
