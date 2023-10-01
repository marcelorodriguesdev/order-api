package br.com.btg.order.api.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalOrderValueResponse {

    private Long pedido;
    private BigDecimal valorTotal;

}
