package br.com.btg.desafioengenharia.controller;

import br.com.btg.desafioengenharia.exception.RestExceptionHandler;
import br.com.btg.desafioengenharia.model.ClienteModel;
import br.com.btg.desafioengenharia.response.PedidoResponse;
import br.com.btg.desafioengenharia.response.PedidosClienteResponse;
import br.com.btg.desafioengenharia.response.ValorTotalPedidoResponse;
import br.com.btg.desafioengenharia.service.ClienteService;
import br.com.btg.desafioengenharia.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {PedidoController.class, RestExceptionHandler.class})
@EnableWebMvc
class PedidoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private PedidoService pedidoService;

    @Test
    void dadoPedidoValidoQuandoChamarPedidosEntaoRetornoOk() throws Exception {
        Long pedidoId = 1L;
        PedidoResponse responseMock = PedidoResponse.builder()
                .codigoPedido(pedidoId)
                .codigoCliente(1L)
                .itens(new ArrayList<>())
                .build();
        given(pedidoService.getPedidoPorId(pedidoId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/pedidos/" + pedidoId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        PedidoResponse pedidoResponse = new ObjectMapper().readValue(contentAsString, PedidoResponse.class);
        assertThat(pedidoResponse.getCodigoPedido().equals(responseMock.getCodigoPedido()));
    }

    @Test
    void dadoPedidoValidoQuandoChamarValorTotalPedidoEntaoRetornoOk() throws Exception {
        Long pedidoId = 1L;
        ValorTotalPedidoResponse responseMock = ValorTotalPedidoResponse.builder()
                .valorTotal(new BigDecimal(10.00))
                .pedido(pedidoId)
                .build();
        given(pedidoService.getValorTotalPedido(pedidoId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/pedidos/" + pedidoId + "/valor-total"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ValorTotalPedidoResponse pedidoResponse = new ObjectMapper().readValue(contentAsString, ValorTotalPedidoResponse.class);
        assertThat(pedidoResponse.getValorTotal().equals(responseMock.getValorTotal()));
    }

    @Test
    void dadoClienteValidoQuandoChamarTodosPedidosEntaoRetornoOk() throws Exception {
        Long clienteId = 1L;
        PedidosClienteResponse responseMock = PedidosClienteResponse.builder()
                .nomeCliente("Caio")
                .build();

        ClienteModel clienteModel = ClienteModel.builder()
                .id(1L)
                .nome("Caio")
                .build();

        given(clienteService.getClientePorId(clienteId)).willReturn(clienteModel);
        given(pedidoService.getPedidosResponseModelPorCliente(clienteModel)).willReturn(responseMock);

        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/pedidos/id-cliente/" + clienteId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        PedidosClienteResponse pedidoResponse = new ObjectMapper().readValue(contentAsString, PedidosClienteResponse.class);
        assertThat(pedidoResponse.getNomeCliente().equals(responseMock.getNomeCliente()));
    }

}