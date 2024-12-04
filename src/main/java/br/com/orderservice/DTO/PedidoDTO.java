package br.com.orderservice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
