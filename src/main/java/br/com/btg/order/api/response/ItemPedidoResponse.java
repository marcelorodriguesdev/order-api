package br.com.btg.order.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ItemPedidoResponse {

    private String produto;
    private Integer quantidade;
    private BigDecimal preco;

}