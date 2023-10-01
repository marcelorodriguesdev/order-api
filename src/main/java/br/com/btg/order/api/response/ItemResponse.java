package br.com.btg.order.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ItemResponse {
    private String product;
    private Integer quantity;
    private BigDecimal price;
}
