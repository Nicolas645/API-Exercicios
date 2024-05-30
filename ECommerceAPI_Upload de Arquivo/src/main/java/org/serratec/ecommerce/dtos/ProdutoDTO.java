package org.serratec.ecommerce.dtos;

public class ProdutoDTO {
	
    private String nome;
    private String descricao;
    private double preco;
    private CategoriaDTO categoria;
    private String url;

    public ProdutoDTO(String nome, String descricao, double preco, CategoriaDTO categoria, String url) {
    	
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.url = url;
    }

    
    
    public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
}
