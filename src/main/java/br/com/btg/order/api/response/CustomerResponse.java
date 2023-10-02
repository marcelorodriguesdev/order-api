package br.com.btg.order.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String message;
    private Long customerId;
    private String customerName;
}
