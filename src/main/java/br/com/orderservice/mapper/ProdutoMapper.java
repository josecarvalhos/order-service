package br.com.orderservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.orderservice.DTO.ProdutoDTO;
import br.com.orderservice.model.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	ProdutoDTO toDTO(Produto Produto);
	
	List<ProdutoDTO> toListDTO(List<Produto> Produtos);
}