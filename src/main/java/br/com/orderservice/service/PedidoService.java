package br.com.orderservice.service;

import org.springframework.stereotype.Service;

import br.com.orderservice.DTO.PedidoDTO;

@Service
public interface PedidoService {

	void consumirPedido(String message);

	PedidoDTO buscarPedidoPorNumero(String numero);

}