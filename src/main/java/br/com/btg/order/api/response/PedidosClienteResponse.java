package br.com.btg.order.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidosClienteResponse {

    private Long idCliente;
    private String nomeCliente;
    private List<PedidoResponse> pedidos;

}
