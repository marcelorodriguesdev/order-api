package br.com.btg.desafioengenharia.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {

    @JsonProperty("codigoPedido")
    private long codigoPedido;

    @JsonProperty("codigoCliente")
    private long codigoCliente;

    @JsonProperty("itens")
    private List<ItemRequest> itens;

    @Override
    public String toString() {
        return "{\"codigoPedido\":\"" + codigoPedido + "\""
                + ", \"codigoCliente\":\"" + codigoCliente + "\""
                + ", \"itens\":" + itens
                + "}";
    }
}