package br.com.orderservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.orderservice.DTO.PedidoDTO;
import br.com.orderservice.DTO.PedidoProdutoDTO;
import br.com.orderservice.mapper.PedidoMapper;
import br.com.orderservice.model.Pedido;
import br.com.orderservice.repository.PedidoRepository;

@SpringBootTest
class PedidoServiceTest {
	
	private final static String PEDIDO_JSON = "{\"numero\":\"123\",\"produtos\":[{\"id\":1,\"nome\":\"Produto 1\",\"preco\":50.00,\"quantidade\":2}],\"valorTotal\":100.00,\"status\":\"PENDENTE\"}";

	@InjectMocks
	private PedidoServiceImpl pedidoService;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private PedidoRepository pedidoRepository;

	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;

	@Mock
	private PedidoMapper pedidoMapper;

	private PedidoDTO pedidoRequestDTO;
	private Pedido pedidoEntity;

	@BeforeEach
	void setUp() {
		// Configura os dados para os testes
		pedidoRequestDTO = new PedidoDTO();
		pedidoRequestDTO.setNumero("123");
		pedidoRequestDTO.setStatus("PENDENTE");
		pedidoRequestDTO.setValorTotal(BigDecimal.valueOf(100.00));
		pedidoRequestDTO
				.setItens(new HashSet<>(Set.of(new PedidoProdutoDTO(null, null, 2, BigDecimal.valueOf(50.00)))));

		pedidoEntity = new Pedido();
		pedidoEntity.setNumero("123");
		pedidoEntity.setValorTotal(BigDecimal.valueOf(100.00)); // Exemplo de total calculado
	}

	@Test
	void testConsumirPedido_Sucesso() throws Exception {
		// Mock para ObjectMapper
		when(objectMapper.readValue(anyString(), eq(PedidoDTO.class))).thenReturn(pedidoRequestDTO);

		// Mock para isDuplicateOrder
		when(pedidoRepository.existsByNumero(anyString())).thenReturn(false);

		// Mock para PedidoMapper
		when(pedidoMapper.toEntity(any(PedidoDTO.class))).thenReturn(pedidoEntity);

		// Mock para KafkaTemplate
		when(kafkaTemplate.send(anyString(), anyString())).thenReturn(null); // KafkaTemplate mock ajustado

		// Chama o método que deve ser testado
		pedidoService.consumirPedido(PEDIDO_JSON);

	}

	@Test
	void testConsumirPedido_PedidoDuplicado() throws Exception {
		// Mock para ObjectMapper
		when(objectMapper.readValue(anyString(), eq(PedidoDTO.class))).thenReturn(pedidoRequestDTO);

		// Mock para isDuplicateOrder retornar true
		when(pedidoRepository.existsByNumero(anyString())).thenReturn(true);

		// Chama o método que deve ser testado
		pedidoService.consumirPedido(PEDIDO_JSON);
	}
}