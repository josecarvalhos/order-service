package br.com.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.orderservice.DTO.PedidoDTO;
import br.com.orderservice.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
    private PedidoService pedidoService;

	@Operation(summary = "Cria um novo pedido", description = "Endpoint para criar pedidos na aplicação")
    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody PedidoDTO pedidoRequestDTO) throws JsonProcessingException {
        // Aqui você pode simular a publicação do pedido no Kafka, se necessário
        String mensagemKafka = new ObjectMapper().writeValueAsString(pedidoRequestDTO);
        pedidoService.consumirPedido(mensagemKafka);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido processado com sucesso.");
    }
	
	@Operation(summary = "Consultar pedido", description = "Endpoint para consultar pedidos pelo número")
	@GetMapping("/{numero}")
    public ResponseEntity<PedidoDTO> buscarPedido(@PathVariable String numero) {
        PedidoDTO pedido = pedidoService.buscarPedidoPorNumero(numero);
        return ResponseEntity.ok(pedido);
    }
}
