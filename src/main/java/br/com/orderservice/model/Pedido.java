package br.com.orderservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido", indexes = { @Index(name = "idx_pedido_status", columnList = "status"),
		@Index(name = "idx_pedido_numero", columnList = "numero") })
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 255)
	private String numero;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal valorTotal;

	@Column(nullable = false, length = 50)
	private String status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<PedidoProduto> itens = new HashSet<>();

	public Pedido() {
	}

	public Pedido(Long id, String numero, BigDecimal valorTotal, String status, LocalDateTime dataCriacao,
			LocalDateTime dataAtualizacao, Set<PedidoProduto> itens) {
		super();
		this.id = id;
		this.numero = numero;
		this.valorTotal = valorTotal;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
		this.itens = itens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Set<PedidoProduto> getItens() {
		return itens;
	}

	public void setItens(Set<PedidoProduto> itens) {
		this.itens = itens;
	}

	public void adicionarItens(Set<PedidoProduto> itens) {
		this.itens.clear();
		itens.forEach(item -> {
			item.setPedido(this);
			this.itens.add(item);
		});
	}
}
