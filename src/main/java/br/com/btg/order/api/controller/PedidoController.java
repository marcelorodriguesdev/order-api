package br.com.btg.order.api.controller;

import br.com.btg.order.infra.database.model.ClienteModel;
import br.com.btg.order.api.response.PedidoResponse;
import br.com.btg.order.api.response.PedidosClienteResponse;
import br.com.btg.order.api.response.ValorTotalPedidoResponse;
import br.com.btg.order.core.service.ClienteService;
import br.com.btg.order.core.service.PedidoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static br.com.btg.order.api.response.ErrorResponse.ERROR_RESPONSE_EXCEPTION_MODEL;

@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;


    @GetMapping(value = "v1/pedidos/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedido retornado com sucesso."),
            @ApiResponse(code = 404, message = "Pedido não localizado.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Erro interno", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public PedidoResponse getPedidoPorId(@PathVariable("id") Long id) {
        return pedidoService.getPedidoPorId(id);
    }


    @GetMapping(value = "v1/pedidos/{id}/valor-total")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valor total retornado com sucesso."),
            @ApiResponse(code = 404, message = "Pedido não localizado.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Erro interno", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public ValorTotalPedidoResponse getValorTotalPedido(@PathVariable("id") Long id) {
        return pedidoService.getValorTotalPedido(id);
    }

    @GetMapping(value = "v1/pedidos/id-cliente/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedidos retornados com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não localizado.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Erro interno", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public PedidosClienteResponse getPedidosPorCliente(@PathVariable("idCliente") Long idCliente) {
        ClienteModel clientePorId = clienteService.getClientePorId(idCliente);
        return pedidoService.getPedidosResponseModelPorCliente(clientePorId);
    }

}
