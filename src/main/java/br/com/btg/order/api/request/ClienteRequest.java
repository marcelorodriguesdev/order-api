package br.com.btg.order.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotNull
    @NotBlank
    private String nome;

}
