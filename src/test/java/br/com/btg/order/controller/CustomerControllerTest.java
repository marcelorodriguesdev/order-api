package br.com.btg.order.controller;

import br.com.btg.order.api.controller.CustomerController;
import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.core.domain.handler.RestExceptionHandler;
import br.com.btg.order.api.request.CustomerRequest;
import br.com.btg.order.api.response.CustomerResponse;
import br.com.btg.order.api.response.ErrorResponse;
import br.com.btg.order.api.response.CustomerOrderQuantityResponse;
import br.com.btg.order.core.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {CustomerController.class, RestExceptionHandler.class})
@EnableWebMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void dadoClienteValidoQuandoChamarQuantidadePedidosEntaoRetornoOk() throws Exception {
        Long clienteId = 1L;
        int quantidadePedidos = 3;
        CustomerOrderQuantityResponse responseMock = CustomerOrderQuantityResponse.builder()
                .ordersQuantity(quantidadePedidos)
                .build();
        given(customerService.getOrderQuantityForCustomerById(clienteId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/customers/" + clienteId + "/orders_quantity"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        CustomerOrderQuantityResponse customerOrderQuantityResponse = new ObjectMapper().readValue(contentAsString, CustomerOrderQuantityResponse.class);
        assertThat(customerOrderQuantityResponse.getOrdersQuantity().equals(responseMock.getOrdersQuantity()));
    }

    @Test
    void dadoClienteValidoQuandoChamarSalvarClienteEntaoRetornoCreated() throws Exception {
        ObjectMapper mapper =  new ObjectMapper();
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("Caio")
                .build();
        CustomerResponse responseMock = CustomerResponse.builder()
                .message("Cliente salvo com sucesso")
                .build();
        given(customerService.salvarCliente(any())).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(post("http://localhost:8081/" + "v1/customers/")
                        .content(mapper.writeValueAsString(customerRequest).getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        CustomerResponse customerResponse = mapper.readValue(contentAsString, CustomerResponse.class);
        assertThat(customerResponse.getMessage().equals(responseMock.getMessage()));
    }

    @Test
    void dadoClienteInvalidoQuandoChamarQuantidadePedidosEntaoRetornoNotFound() throws Exception {
        Long clienteId = 1L;
        given(customerService.getOrderQuantityForCustomerById(clienteId)).willThrow(new NotFoundException("Cliente não localizado"));
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/customers/" + clienteId + "/orders_quantity"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getMessage().equals("Cliente não localizado"));
    }

    @Test
    void dadoClienteInvalidoQuandoErroInternoEntaoRetornoInternalServerError() throws Exception {
        Long clienteId = 1L;
        given(customerService.getOrderQuantityForCustomerById(clienteId)).willThrow(new RuntimeException("Erro interno"));
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/customers/" + clienteId + "/orders_quantity"))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getCode().equals("500"));
    }


    @Test
    void dadoClienteInvalidoQuandoChamarSalvarClienteEntaoRetornoBadRequest() throws Exception {
        ObjectMapper mapper =  new ObjectMapper();
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("")
                .build();
        MockHttpServletResponse response = mvc
                .perform(post("http://localhost:8081/" + "v1/customers")
                        .content(mapper.writeValueAsString(customerRequest).getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getCode().equals(400));
    }

}