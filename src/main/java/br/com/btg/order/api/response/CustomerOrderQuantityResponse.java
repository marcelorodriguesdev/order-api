package br.com.btg.order.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderQuantityResponse {

    private Long customerId;
    private String customerName;
    private Integer ordersQuantity;
}
