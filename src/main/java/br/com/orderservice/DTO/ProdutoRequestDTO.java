package br.com.orderservice.DTO;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutoRequestDTO {

	private Long id;

	@NotNull(message = "O nome do produto não pode ser nulo.")
	@Size(min = 3, max = 255, message = "O nome do pedido deve ter entre 3 e 255 caracteres.")
	private String nome;

	@NotNull(message = "O preco do produto não pode ser nulo.")
	@Positive(message = "O preco deve ser maior que zero.")
	private BigDecimal preco;

	@NotNull(message = "A quantidade de produtos não pode ser nulo.")
	@Positive(message = "A quantidade deve ser maior que zero.")
	private int quantidade;

	public ProdutoRequestDTO() {
	}

	public ProdutoRequestDTO(Long id, String nome, BigDecimal preco, int quantidade) {
		super();
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoRequestDTO other = (ProdutoRequestDTO) obj;
		return Objects.equals(id, other.id);
	}

}
