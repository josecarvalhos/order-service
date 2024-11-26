package br.com.orderservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.orderservice.DTO.PedidoDTO;
import br.com.orderservice.DTO.PedidoProdutoDTO;
import br.com.orderservice.mapper.PedidoMapper;
import br.com.orderservice.mapper.ProdutoMapper;
import br.com.orderservice.model.Pedido;
import br.com.orderservice.model.PedidoProduto;
import br.com.orderservice.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private PedidoMapper pedidoMapper;

	@Autowired
	private ProdutoMapper produtoMapper;

	@Value("${spring.kafka.topics.input}")
	private String inputTopic;

	@Value("${spring.kafka.topics.output}")
	private String outputTopic;

	@Override
	@KafkaListener(topics = "${spring.kafka.topics.input}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumirPedido(String message) {
		CompletableFuture.runAsync(() -> {
			try {
				PedidoDTO pedidoDTO = objectMapper.readValue(message, PedidoDTO.class);

				// Verificar duplicação
				if (isDuplicateOrder(pedidoDTO.getNumero())) {
					log.warn("Pedido duplicado: {}", pedidoDTO.getNumero());
					return;
				}

				// Iniciar pedido com estado PENDENTE
				pedidoDTO.setStatus("PENDENTE");
				Pedido pedido = processarPedido(pedidoDTO);

				// Publicar no Kafka (estado inicial)
				publishPedido(pedidoDTO);

				if (pedido != null) {
					// Decidir próximo estado (APROVADO ou CANCELADO)
					transitarStatus(pedidoDTO, pedido);
				}

			} catch (Exception e) {
				log.error("Erro ao consumir mensagem: {}", message, e);
			}
		});
	}

	private void transitarStatus(PedidoDTO pedidoDTO, Pedido pedido) {
		pedidoDTO.setDataAtualizacao(LocalDateTime.now());
		if (validarPedido(pedidoDTO)) {
			pedidoDTO.setStatus("APROVADO");
			atualizarStatus(pedidoDTO, pedido);
		} else {
			pedidoDTO.setStatus("CANCELADO");
			atualizarStatus(pedidoDTO, pedido);
		}
	}

	@Transactional
	private void atualizarStatus(PedidoDTO pedidoDTO, Pedido pedido) {
		pedido.setStatus(pedidoDTO.getStatus());
		pedido.setDataAtualizacao(pedidoDTO.getDataAtualizacao());
		pedidoRepository.saveAndFlush(pedido);
		log.info("Status atualizado para {}: Pedido {}", pedidoDTO.getStatus(), pedidoDTO.getNumero());

		// Publicar o novo status no Kafka
		publishPedido(pedidoDTO);
	}

	private boolean isDuplicateOrder(String numero) {
		return pedidoRepository.existsByNumero(numero);
	}

	@Transactional
	private Pedido processarPedido(PedidoDTO pedidoDTO) {
		BigDecimal total = calcularPedido(pedidoDTO);
		pedidoDTO.setValorTotal(total);
		pedidoDTO.setDataCriacao(LocalDateTime.now());
		Pedido pedido = pedidoMapper.toEntity(pedidoDTO);

		Set<PedidoProduto> itens = new HashSet<>(pedido.getItens());

		pedido.adicionarItens(itens);

		pedido = pedidoRepository.save(pedido);
		log.info("Pedido processado e salvo com status PENDENTE: {}", pedidoDTO.getNumero());
		return pedido;
	}

	private BigDecimal calcularPedido(PedidoDTO pedidoDTO) {
		return pedidoDTO.getItens().stream()
				.map(produto -> produto.getPrecoUnitario().multiply(BigDecimal.valueOf(produto.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void publishPedido(PedidoDTO pedido) {
		try {
			String orderMessage = objectMapper.writeValueAsString(pedido);
			kafkaTemplate.send(outputTopic, orderMessage);
			log.info("Pedido publicado no tópico de saída: {}", pedido.getNumero());
		} catch (JsonProcessingException e) {
			log.error("Erro ao serializar o pedido para envio no Kafka: {}", pedido.getNumero(), e);
		}
	}

	private boolean validarPedido(PedidoDTO pedidoDTO) {
		int estoque = 10;
		int total = somarQuantidade(pedidoDTO);
		return total < estoque ? true : false;
	}

	private int somarQuantidade(PedidoDTO pedidoDTO) {
		return pedidoDTO.getItens().stream().mapToInt(PedidoProdutoDTO::getQuantidade).sum();
	}

	public PedidoDTO buscarPedidoPorNumero(String numero) {
		Pedido pedido = pedidoRepository.findByNumero(numero)
				.orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado: " + numero));

		return toDTO(pedido);
	}

	public PedidoDTO toDTO(Pedido pedido) {
		if (pedido == null) {
			return null;
		}

		PedidoDTO pedidoDTO = new PedidoDTO();

		Set<PedidoProdutoDTO> itens = pedido.getItens().stream().map(item -> new PedidoProdutoDTO(pedidoDTO,
				produtoMapper.toDTO(item.getProduto()), item.getQuantidade(), item.getPrecoUnitario()))
				.collect(Collectors.toSet());

		pedidoDTO.setNumero(pedido.getNumero());
		pedidoDTO.setItens(itens);
		pedidoDTO.setValorTotal(pedido.getValorTotal());
		pedidoDTO.setStatus(pedido.getStatus());
		pedidoDTO.setDataCriacao(pedido.getDataCriacao());
		pedidoDTO.setDataAtualizacao(pedido.getDataAtualizacao());

		return pedidoDTO;
	}

}
