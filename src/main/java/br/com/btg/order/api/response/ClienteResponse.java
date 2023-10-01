package br.com.btg.order.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private String mensagem;
    private Long idCliente;
    private String nomeCliente;

}
