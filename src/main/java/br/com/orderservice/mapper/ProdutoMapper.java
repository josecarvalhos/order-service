package br.com.orderservice.mapper;

import org.mapstruct.Mapper;

import br.com.orderservice.DTO.ProdutoRequestDTO;
import br.com.orderservice.model.Produtos;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	Produtos toEntity(ProdutoRequestDTO ProdutoDTO);
}