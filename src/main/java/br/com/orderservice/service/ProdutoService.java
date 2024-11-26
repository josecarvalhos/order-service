package br.com.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.orderservice.DTO.ProdutoDTO;

@Service
public interface ProdutoService {

	List<ProdutoDTO> ListarTodos();

}