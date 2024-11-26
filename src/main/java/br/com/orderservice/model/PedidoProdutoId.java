package br.com.orderservice.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PedidoProdutoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "pedido_id")
	private Long pedidoId;
	
	@Column(name = "produto_id")
	private Long produtoId;

	public PedidoProdutoId() {
	}

	public PedidoProdutoId(Long pedidoId, Long produtoId) {
		this.pedidoId = pedidoId;
		this.produtoId = produtoId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    PedidoProdutoId that = (PedidoProdutoId) o;
	    return Objects.equals(pedidoId, that.pedidoId) &&
	           Objects.equals(produtoId, that.produtoId);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(pedidoId, produtoId);
	}

}
