package br.com.btg.order.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @JsonProperty("codigoPedido")
    private long orderId;

    @JsonProperty("codigoCliente")
    private long customerId;

    @JsonProperty("itens")
    private List<ItemRequest> items;
}
