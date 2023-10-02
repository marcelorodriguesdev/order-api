package br.com.btg.order.api.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalOrderAmountResponse {
    private Long order;
    private BigDecimal totalAmount;
}
