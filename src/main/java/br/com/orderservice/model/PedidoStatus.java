package br.com.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PedidoStatus {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    CANCELADO("Cancelado");

    private final String descricao;
    
    PedidoStatus(String descricao) {
        this.descricao = descricao;
    }

	public String getDescricao() {
		return descricao;
	}
}
