package br.com.orderservice.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProdutoDTO {

	@JsonBackReference
	private PedidoDTO pedido;
	private ProdutoDTO produto;
	private int quantidade;
	private BigDecimal precoUnitario;

}
