package br.com.orderservice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PedidoDTO {
	
	

	@NotNull(message = "O número do pedido não pode ser nulo.")
	@Size(min = 3, max = 255, message = "O número do pedido deve ter entre 3 e 255 caracteres.")
	private String numero;

	@NotEmpty(message = "A lista de produtos não pode ser vazia.")
	@JsonManagedReference
	private Set<PedidoProdutoDTO> itens;

	private BigDecimal valorTotal;
	private String status;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataAtualizacao;

	public PedidoDTO() {
	}

	public PedidoDTO(String numero, Set<PedidoProdutoDTO> itens, BigDecimal valorTotal, String status,
			LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
		this.numero = numero;
		this.itens = itens;
		this.valorTotal = valorTotal;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Set<PedidoProdutoDTO> getItens() {
		return itens;
	}

	public void setItens(Set<PedidoProdutoDTO> itens) {
		this.itens = itens;
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
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoDTO other = (PedidoDTO) obj;
		return Objects.equals(numero, other.numero);
	}

}
