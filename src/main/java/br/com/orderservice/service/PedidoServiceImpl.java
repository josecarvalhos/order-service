package br.com.orderservice.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.orderservice.DTO.PedidoRequestDTO;
import br.com.orderservice.mapper.PedidoMapper;
import br.com.orderservice.model.Pedidos;
import br.com.orderservice.repository.PedidoRepository;

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

	@Value("${spring.kafka.topics.input}")
	private String inputTopic;

	@Value("${spring.kafka.topics.output}")
	private String outputTopic;

	@Override
	@KafkaListener(topics = "${spring.kafka.topics.input}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumirPedido(String message) {
		try {
			PedidoRequestDTO pedido = objectMapper.readValue(message, PedidoRequestDTO.class);

			// Verificar duplicação
			if (isDuplicateOrder(pedido.getNumero())) {
				log.warn("Pedido duplicado: {}", pedido.getNumero());
				return;
			}

			// Processar pedido
			processarPedido(pedido);

			// Publicar no Kafka
			publishPedido(pedido);

		} catch (Exception e) {
			log.error("Erro ao consumir mensagem: {}", message, e);
		}
	}

	private boolean isDuplicateOrder(String numero) {
		return pedidoRepository.existsByNumero(numero);
	}

	private void processarPedido(PedidoRequestDTO pedidoDTO) {
		BigDecimal total = calcularPedido(pedidoDTO);
		pedidoDTO.setValorTotal(total);
		pedidoDTO.setStatus("APROVADO");
		Pedidos pedido = pedidoMapper.toEntity(pedidoDTO);

		pedidoRepository.save(pedido);
		log.info("Pedido processado e salvo: {}", pedidoDTO.getNumero());
	}

	// Método auxiliar para calcular o total de um pedido
	private BigDecimal calcularPedido(PedidoRequestDTO pedidoDTO) {
		return pedidoDTO.getProdutos().stream()
				.map(produto -> produto.getPreco().multiply(BigDecimal.valueOf(produto.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void publishPedido(PedidoRequestDTO order) {
		try {
			String orderMessage = objectMapper.writeValueAsString(order);
			kafkaTemplate.send(outputTopic, orderMessage);
			log.info("Pedido publicado no tópico de saída: {}", order.getNumero());
		} catch (JsonProcessingException e) {
			log.error("Erro ao serializar o pedido para envio no Kafka: {}", order.getNumero(), e);
		}
	}

}
