package br.com.btg.desafioengenharia.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValorTotalPedidoResponse {

    private Long pedido;
    private BigDecimal valorTotal;

}
