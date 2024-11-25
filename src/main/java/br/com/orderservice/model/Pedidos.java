package br.com.orderservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pedidos {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produtos> produtos;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    private String status;

    @Column
    private LocalDateTime dataCriacao;

    @Column
    private LocalDateTime dataAtualizacao;
    
    

	public Pedidos() {
	}

	public Pedidos(Long id, String numero, List<Produtos> produtos, BigDecimal valorTotal, String status,
			LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
		this.id = id;
		this.numero = numero;
		this.produtos = produtos;
		this.valorTotal = valorTotal;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
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

	public List<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos> produtos) {
		this.produtos = produtos;
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
		Pedidos other = (Pedidos) obj;
		return Objects.equals(id, other.id);
	}
    
}
