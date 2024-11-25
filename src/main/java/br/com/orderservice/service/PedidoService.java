package br.com.orderservice.service;

import org.springframework.stereotype.Service;

@Service
public interface PedidoService {

	void consumirPedido(String message);

}