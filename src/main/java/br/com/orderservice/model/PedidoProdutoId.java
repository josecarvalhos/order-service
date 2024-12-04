package br.com.orderservice.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PedidoProdutoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "pedido_id")
	private Long pedidoId;
	
	@Column(name = "produto_id")
	private Long produtoId;

}
