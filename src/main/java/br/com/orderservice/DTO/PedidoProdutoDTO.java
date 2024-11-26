package br.com.orderservice.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class PedidoProdutoDTO {

	@JsonBackReference
	private PedidoDTO pedido;
	private ProdutoDTO produto;
	private int quantidade;
	private BigDecimal precoUnitario;

	public PedidoProdutoDTO() {
	}

	public PedidoProdutoDTO(PedidoDTO pedido, ProdutoDTO produto, int quantidade, BigDecimal precoUnitario) {
		this.pedido = pedido;
		this.pedido = pedido;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
	}

	public PedidoDTO getPedido() {
		return pedido;
	}

	public void setPedido(PedidoDTO pedido) {
		this.pedido = pedido;
	}

	public ProdutoDTO getProduto() {
		return produto;
	}

	public void setProduto(ProdutoDTO produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

}
