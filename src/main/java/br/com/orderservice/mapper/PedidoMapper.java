package br.com.orderservice.mapper;

import org.mapstruct.Mapper;

import br.com.orderservice.DTO.PedidoDTO;
import br.com.orderservice.model.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido toEntity(PedidoDTO pedidoDTO);
    
    PedidoDTO toDTO(Pedido pedido);
}
