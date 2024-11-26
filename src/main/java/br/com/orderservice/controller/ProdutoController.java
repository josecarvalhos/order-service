package br.com.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.orderservice.DTO.ProdutoDTO;
import br.com.orderservice.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
    private ProdutoService produtoService;

	@Operation(summary = "Listar Produtos", description = "Endpoint para listar produtos cadastrados")
	@GetMapping("/{numero}")
    public ResponseEntity<List<ProdutoDTO>> buscarPedido() {
        List<ProdutoDTO> pedidos = produtoService.ListarTodos();
        return ResponseEntity.ok(pedidos);
    }
}
