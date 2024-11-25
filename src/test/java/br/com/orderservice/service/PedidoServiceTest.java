package br.com.orderservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.orderservice.DTO.PedidoRequestDTO;
import br.com.orderservice.DTO.ProdutoRequestDTO;
import br.com.orderservice.mapper.PedidoMapper;
import br.com.orderservice.model.Pedidos;
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

	private PedidoRequestDTO pedidoRequestDTO;
	private Pedidos pedidoEntity;

	@BeforeEach
	void setUp() {
		// Configura os dados para os testes
		pedidoRequestDTO = new PedidoRequestDTO();
		pedidoRequestDTO.setNumero("123");
		pedidoRequestDTO.setStatus("PENDENTE");
		pedidoRequestDTO.setValorTotal(BigDecimal.valueOf(100.00));
		pedidoRequestDTO
				.setProdutos(new ArrayList<>(List.of(new ProdutoRequestDTO(1L, "Produto 1", BigDecimal.valueOf(50.00), 2))));

		pedidoEntity = new Pedidos();
		pedidoEntity.setNumero("123");
		pedidoEntity.setValorTotal(BigDecimal.valueOf(100.00)); // Exemplo de total calculado
	}

	@Test
	void testConsumirPedido_Sucesso() throws Exception {
		// Mock para ObjectMapper
		when(objectMapper.readValue(anyString(), eq(PedidoRequestDTO.class))).thenReturn(pedidoRequestDTO);

		// Mock para isDuplicateOrder
		when(pedidoRepository.existsByNumero(anyString())).thenReturn(false);

		// Mock para PedidoMapper
		when(pedidoMapper.toEntity(any(PedidoRequestDTO.class))).thenReturn(pedidoEntity);

		// Mock para KafkaTemplate
		when(kafkaTemplate.send(anyString(), anyString())).thenReturn(null); // KafkaTemplate mock ajustado

		// Chama o método que deve ser testado
		pedidoService.consumirPedido(PEDIDO_JSON);

		// Verifica se os métodos foram chamados corretamente
		verify(pedidoRepository, times(1)).save(any(Pedidos.class));

	}

	@Test
	void testConsumirPedido_PedidoDuplicado() throws Exception {
		// Mock para ObjectMapper
		when(objectMapper.readValue(anyString(), eq(PedidoRequestDTO.class))).thenReturn(pedidoRequestDTO);

		// Mock para isDuplicateOrder retornar true
		when(pedidoRepository.existsByNumero(anyString())).thenReturn(true);

		// Chama o método que deve ser testado
		pedidoService.consumirPedido(PEDIDO_JSON);

		// Verifica que o método save não foi chamado
		verify(pedidoRepository, never()).save(any());

		// Verifica que o método KafkaTemplate não foi chamado
		verify(kafkaTemplate, never()).send(anyString(), anyString());
	}
}