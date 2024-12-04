package br.com.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PedidoStatus {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    CANCELADO("Cancelado");

    private final String descricao;

}
