package br.com.btg.order.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantidadePedidosClienteResponse {

    private Long idCLiente;
    private String nomeCLiente;
    private Integer quantidadePedidos;

}
