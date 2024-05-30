package org.serratec.ecommerce.entities;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "minha_sequencia", sequenceName = "minha_sequencia", allocationSize = 1)
    private Long id; 
    
    @NotBlank(message="O nome é obrigatório!")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "Primeira letra do nome deve ser maiúscula!")
    private String nome;
    
    @NotBlank(message = "O telefone é obrigatório!")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", message = "Formato de telefone inválido.")
    private String telefone;
    
    @NotBlank(message="O e-mail é obrigatório!")
    @Email(message="O e-mail deve ser válido!")
    @Column(unique = true)
    private String email;
    
    @NotBlank(message="A senha é obrigatória!")
    private String senha;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;

   
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    
    private String fotourl;
    
    @NotBlank
    @CPF(message = "CPF inválido")
    @Column(nullable = false, unique = true)
    private String cpf;
    
    @NotBlank(message = "O CEP é obrigatório!")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido.")
    private String cep;
      
    
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
    
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	
	public String getFotourl() {
		return fotourl;
	}

	public void setFotourl(String fotourl) {
		this.fotourl = fotourl;
	}

	@Override
    public String toString() {
        return "nome: " + nome + "email: " + email + "\n\n\n";
}
    
}
