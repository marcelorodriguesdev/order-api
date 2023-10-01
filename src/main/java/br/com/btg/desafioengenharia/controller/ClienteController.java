package br.com.btg.desafioengenharia.controller;

import br.com.btg.desafioengenharia.request.ClienteRequest;
import br.com.btg.desafioengenharia.response.ClienteResponse;
import br.com.btg.desafioengenharia.response.QuantidadePedidosClienteResponse;
import br.com.btg.desafioengenharia.service.ClienteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.btg.desafioengenharia.response.ErrorResponse.ERROR_RESPONSE_EXCEPTION_MODEL;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "v1/clientes/{id}/quantidade-pedidos")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantidade de pedidos retornado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não localizado.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Erro interno", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public QuantidadePedidosClienteResponse getQuantidadePedidosCliente(@PathVariable("id") Long id) {
        return clienteService.getQuantidadePedidosClientePorId(id);
    }

    @PostMapping(value = "v1/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public ClienteResponse postCliente(@RequestBody @Valid ClienteRequest clienteRequest) {
        return clienteService.salvarCliente(clienteRequest);
    }

}
