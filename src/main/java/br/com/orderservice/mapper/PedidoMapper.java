package br.com.orderservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.orderservice.DTO.PedidoRequestDTO;
import br.com.orderservice.DTO.ProdutoRequestDTO;
import br.com.orderservice.model.Pedidos;
import br.com.orderservice.model.Produtos;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

//	@Mapping(target = "produtos", source = "produtos")
    Pedidos toEntity(PedidoRequestDTO pedidoDTO);
    
//    List<Produtos> toEntityList(List<ProdutoRequestDTO> produtoDTO);
//
//    Produtos toEntity(ProdutoRequestDTO produtoDTO);
}
