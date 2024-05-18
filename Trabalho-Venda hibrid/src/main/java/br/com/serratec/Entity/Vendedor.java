package br.com.serratec.Entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Vendedor")
public class Vendedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoVendedor;
	
	@NotEmpty(message = "Nome nulo")
	private String nome;
	
	@Email(message = "Email invalido")
	private String email;
	
	@Min(value = 1045, message = "Valor minimo não atingido, pague mais")
	private BigDecimal salario;
	
	
	private BigDecimal comissao;
	
	@OneToMany(mappedBy = "vendedor")
	private List<LancamentoVendas> lancamentos;
	
	
	public List<LancamentoVendas> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<LancamentoVendas> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public Long getCodigoVendedor() {
		return codigoVendedor;
	}
	public void setCodigoVendedor(Long codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
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
	
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	public BigDecimal getComissao() {
		return comissao;
	}
	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	
	
}
