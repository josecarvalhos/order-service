package br.com.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.orderservice.DTO.PedidoRequestDTO;
import br.com.orderservice.service.PedidoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

	@Autowired
    private PedidoService pedidoService;

    // Endpoint para criar pedido (caso queira simular a criação manual)
    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) throws JsonProcessingException {
        // Aqui você pode simular a publicação do pedido no Kafka, se necessário
        String mensagemKafka = new ObjectMapper().writeValueAsString(pedidoRequestDTO);
        pedidoService.consumirPedido(mensagemKafka);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido processado com sucesso.");
    }
}
