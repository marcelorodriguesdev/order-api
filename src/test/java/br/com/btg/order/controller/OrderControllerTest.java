package br.com.btg.order.controller;

import br.com.btg.order.api.controller.OrderController;
import br.com.btg.order.core.domain.handler.RestExceptionHandler;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.api.response.CustomerOrdersResponse;
import br.com.btg.order.api.response.TotalOrderValueResponse;
import br.com.btg.order.core.service.CustomerService;
import br.com.btg.order.core.service.OrderService;
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
@SpringBootTest(classes = {OrderController.class, RestExceptionHandler.class})
@EnableWebMvc
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private OrderService orderService;

    @Test
    void dadoPedidoValidoQuandoChamarValorTotalPedidoEntaoRetornoOk() throws Exception {
        Long pedidoId = 1L;
        TotalOrderValueResponse responseMock = TotalOrderValueResponse.builder()
                .valorTotal(new BigDecimal(10.00))
                .pedido(pedidoId)
                .build();
        given(orderService.getTotalOrderValue(pedidoId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/orders/" + pedidoId + "/total_amount"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        TotalOrderValueResponse pedidoResponse = new ObjectMapper().readValue(contentAsString, TotalOrderValueResponse.class);
        assertThat(pedidoResponse.getValorTotal().equals(responseMock.getValorTotal()));
    }

    @Test
    void dadoClienteValidoQuandoChamarTodosPedidosEntaoRetornoOk() throws Exception {
        Long clienteId = 1L;
        CustomerOrdersResponse responseMock = CustomerOrdersResponse.builder()
                .nomeCliente("Caio")
                .build();

        CustomerModel customerModel = CustomerModel.builder()
                .id(1L)
                .name("Caio")
                .build();

        given(customerService.getCustomerById(clienteId)).willReturn(customerModel);
        given(orderService.getOrdersByCustomer(customerModel)).willReturn(responseMock);

        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/orders/" + clienteId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        CustomerOrdersResponse pedidoResponse = new ObjectMapper().readValue(contentAsString, CustomerOrdersResponse.class);
        assertThat(pedidoResponse.getNomeCliente().equals(responseMock.getNomeCliente()));
    }

}