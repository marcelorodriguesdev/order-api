package br.com.btg.desafioengenharia.controller;

import br.com.btg.desafioengenharia.exception.NotFoundException;
import br.com.btg.desafioengenharia.exception.RestExceptionHandler;
import br.com.btg.desafioengenharia.request.ClienteRequest;
import br.com.btg.desafioengenharia.response.ClienteResponse;
import br.com.btg.desafioengenharia.response.ErrorResponse;
import br.com.btg.desafioengenharia.response.QuantidadePedidosClienteResponse;
import br.com.btg.desafioengenharia.service.ClienteService;
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
@SpringBootTest(classes = {ClienteController.class, RestExceptionHandler.class})
@EnableWebMvc
class ClienteControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void dadoClienteValidoQuandoChamarQuantidadePedidosEntaoRetornoOk() throws Exception {
        Long clienteId = 1L;
        int quantidadePedidos = 3;
        QuantidadePedidosClienteResponse responseMock = QuantidadePedidosClienteResponse.builder()
                .quantidadePedidos(quantidadePedidos)
                .build();
        given(clienteService.getQuantidadePedidosClientePorId(clienteId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/clientes/" + clienteId + "/quantidade-pedidos"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        QuantidadePedidosClienteResponse quantidadePedidosClienteResponse = new ObjectMapper().readValue(contentAsString, QuantidadePedidosClienteResponse.class);
        assertThat(quantidadePedidosClienteResponse.getQuantidadePedidos().equals(responseMock.getQuantidadePedidos()));
    }

    @Test
    void dadoClienteValidoQuandoChamarSalvarClienteEntaoRetornoCreated() throws Exception {
        ObjectMapper mapper =  new ObjectMapper();
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .nome("Caio")
                .build();
        ClienteResponse responseMock = ClienteResponse.builder()
                .mensagem("Cliente salvo com sucesso")
                .build();
        given(clienteService.salvarCliente(any())).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(post("http://localhost:8081/" + "v1/clientes")
                        .content(mapper.writeValueAsString(clienteRequest).getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ClienteResponse clienteResponse = mapper.readValue(contentAsString, ClienteResponse.class);
        assertThat(clienteResponse.getMensagem().equals(responseMock.getMensagem()));
    }

    @Test
    void dadoClienteInvalidoQuandoChamarQuantidadePedidosEntaoRetornoNotFound() throws Exception {
        Long clienteId = 1L;
        given(clienteService.getQuantidadePedidosClientePorId(clienteId)).willThrow(new NotFoundException("Cliente não localizado"));
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/clientes/" + clienteId + "/quantidade-pedidos"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getMessage().equals("Cliente não localizado"));
    }

    @Test
    void dadoClienteInvalidoQuandoErroInternoEntaoRetornoInternalServerError() throws Exception {
        Long clienteId = 1L;
        given(clienteService.getQuantidadePedidosClientePorId(clienteId)).willThrow(new RuntimeException("Erro interno"));
        MockHttpServletResponse response = mvc
                .perform(get("http://localhost:8081/" + "v1/clientes/" + clienteId + "/quantidade-pedidos"))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getCode().equals("500"));
    }


    @Test
    void dadoClienteInvalidoQuandoChamarSalvarClienteEntaoRetornoBadRequest() throws Exception {
        ObjectMapper mapper =  new ObjectMapper();
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .nome("")
                .build();
        MockHttpServletResponse response = mvc
                .perform(post("http://localhost:8081/" + "v1/clientes")
                        .content(mapper.writeValueAsString(clienteRequest).getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getCode().equals(400));
    }

}