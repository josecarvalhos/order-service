package br.com.orderservice.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PedidoRequestDTO {

	@NotNull(message = "O número do pedido não pode ser nulo.")
	@Size(min = 3, max = 255, message = "O número do pedido deve ter entre 3 e 255 caracteres.")
	private String numero;

	@NotEmpty(message = "A lista de produtos não pode ser vazia.")
	private List<ProdutoRequestDTO> produtos;

	private BigDecimal valorTotal;

	private String status;

	public PedidoRequestDTO() {
	}

	public PedidoRequestDTO(String numero, List<ProdutoRequestDTO> produtos, BigDecimal valorTotal,
			String status) {
		super();
		this.numero = numero;
		this.produtos = produtos;
		this.valorTotal = valorTotal;
		this.status = status;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<ProdutoRequestDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoRequestDTO> produtos) {
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
		PedidoRequestDTO other = (PedidoRequestDTO) obj;
		return Objects.equals(numero, other.numero);
	}

}
