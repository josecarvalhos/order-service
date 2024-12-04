package br.com.orderservice.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

	private Long id;
	private String nome;
	private BigDecimal preco;
	private int quantidade;

}
