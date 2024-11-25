package br.com.orderservice.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pedido_id", nullable = false)
//    private Pedidos pedido;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal preco;

	@Column(nullable = false)
	private int quantidade;

	public Produtos() {
	}

	public Produtos(Long id, String nome, BigDecimal preco, int quantidade) {
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
		Produtos other = (Produtos) obj;
		return Objects.equals(id, other.id);
	}

}
