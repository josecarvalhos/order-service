package br.com.orderservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto", indexes = { @Index(name = "idx_produto_nome", columnList = "nome"),
		@Index(name = "idx_produto_preco", columnList = "preco") })
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String nome;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal preco;

	@Column(nullable = false)
	private Integer estoque;

	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

}
