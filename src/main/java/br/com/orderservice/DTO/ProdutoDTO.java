package br.com.orderservice.DTO;

import java.math.BigDecimal;

public class ProdutoDTO {

	private Long id;
	private String nome;
	private BigDecimal preco;
	private int quantidade;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Long id, String nome, BigDecimal preco, int quantidade) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
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

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
