package br.com.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.orderservice.DTO.ProdutoDTO;
import br.com.orderservice.mapper.ProdutoMapper;
import br.com.orderservice.model.Produto;
import br.com.orderservice.repository.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepositoryu;

	@Autowired
	private ProdutoMapper produtoMapper;


	@Override
	public List<ProdutoDTO> ListarTodos() {
		List<Produto> produtos = produtoRepositoryu.findAll();

		return produtoMapper.toListDTO(produtos);
	}

}
