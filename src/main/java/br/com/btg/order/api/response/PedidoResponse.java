package br.com.btg.order.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

    private Long codigoPedido;
    private Long codigoCliente;
    private List<ItemPedidoResponse> itens;

}
