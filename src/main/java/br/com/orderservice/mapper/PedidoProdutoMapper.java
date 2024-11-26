package br.com.orderservice.mapper;

import org.mapstruct.Mapper;

import br.com.orderservice.DTO.PedidoProdutoDTO;
import br.com.orderservice.model.PedidoProduto;

@Mapper(componentModel = "spring")
public interface PedidoProdutoMapper {

    PedidoProduto toEntity(PedidoProdutoDTO dto);

    PedidoProdutoDTO toDTO(PedidoProduto entity);
}