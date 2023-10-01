package br.com.btg.order.controller;

import br.com.btg.order.api.controller.PedidoController;
import br.com.btg.order.core.domain.handler.RestExceptionHandler;
import br.com.btg.order.infra.database.model.ClienteModel;
import br.com.btg.order.api.response.PedidosClienteResponse;
import br.com.btg.order.api.response.ValorTotalPedidoResponse;
import br.com.btg.order.core.service.ClienteService;
import br.com.btg.order.core.service.PedidoService;
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
    void dadoPedidoValidoQuandoChamarValorTotalPedidoEntaoRetornoOk() throws Exception {
        Long pedidoId = 1L;
        ValorTotalPedidoResponse responseMock = ValorTotalPedidoResponse.builder()
                .valorTotal(new BigDecimal(10.00))
                .pedido(pedidoId)
                .build();
        given(pedidoService.getValorTotalPedido(pedidoId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/orders/" + pedidoId + "/valor-total"))
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
                .name("Caio")
                .build();

        given(clienteService.getClientePorId(clienteId)).willReturn(clienteModel);
        given(pedidoService.getPedidosResponseModelPorCliente(clienteModel)).willReturn(responseMock);

        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/orders/id-cliente/" + clienteId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        PedidosClienteResponse pedidoResponse = new ObjectMapper().readValue(contentAsString, PedidosClienteResponse.class);
        assertThat(pedidoResponse.getNomeCliente().equals(responseMock.getNomeCliente()));
    }

}