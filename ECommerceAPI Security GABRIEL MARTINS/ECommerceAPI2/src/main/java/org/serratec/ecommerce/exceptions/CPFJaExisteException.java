package org.serratec.ecommerce.exceptions;

public class CPFJaExisteException extends RuntimeException {

	private static final long serialVersionUID = -7573831960590217486L;
	
	private String cpfExistente;

	public CPFJaExisteException(String mensagem, String cpfExistente) {
		super(mensagem);
		this.cpfExistente = cpfExistente;
	}

	public String getCpfExistente() {
		return cpfExistente;
	}
}
