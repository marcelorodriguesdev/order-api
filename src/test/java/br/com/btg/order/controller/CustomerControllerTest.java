package br.com.btg.order.controller;

import br.com.btg.order.api.controller.CustomerController;
import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.core.domain.handler.ExceptionHandlerAdvice;
import br.com.btg.order.api.response.ErrorResponse;
import br.com.btg.order.api.response.CustomerOrderQuantityResponse;
import br.com.btg.order.core.service.CustomerService;
import br.com.btg.order.utils.TestApiConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@SpringBootTest(classes = {CustomerController.class, ExceptionHandlerAdvice.class})
@EnableWebMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void shouldReturnOkWhenValidCustomerCallsGetOrdersQuantity() throws Exception {
        Long customerId = 1L;
        int ordersQuantity = 3;

        CustomerOrderQuantityResponse responseMock = CustomerOrderQuantityResponse.builder()
                .ordersQuantity(ordersQuantity)
                .build();
        given(customerService.getOrderQuantityForCustomerById(customerId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get(TestApiConstants.BASE_URL + TestApiConstants.CUSTOMERS_PATH + customerId + TestApiConstants.ORDERS_QUANTITY_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        CustomerOrderQuantityResponse customerOrderQuantityResponse = new ObjectMapper().readValue(contentAsString, CustomerOrderQuantityResponse.class);
        assertThat(customerOrderQuantityResponse.getOrdersQuantity().equals(responseMock.getOrdersQuantity()));
    }

    @Test
    void shouldReturnNotFoundWhenInvalidCustomerCallsGetOrdersQuantity() throws Exception {
        Long customerId = 1L;

        given(customerService.getOrderQuantityForCustomerById(customerId)).willThrow(new NotFoundException("Customer not found"));
        MockHttpServletResponse response = mvc
                .perform(get(TestApiConstants.BASE_URL + TestApiConstants.CUSTOMERS_PATH + customerId + TestApiConstants.ORDERS_QUANTITY_PATH))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getMessage().equals("Customer not found."));
    }

    @Test
    void shouldReturnInternalServerErrorWhenInvalidCustomerCausesInternalError() throws Exception {
        Long customerId = 1L;

        given(customerService.getOrderQuantityForCustomerById(customerId)).willThrow(new RuntimeException("Internal server error"));
        MockHttpServletResponse response = mvc
                .perform(get(TestApiConstants.BASE_URL + TestApiConstants.CUSTOMERS_PATH + customerId + TestApiConstants.ORDERS_QUANTITY_PATH))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        ErrorResponse errorResponse = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getCode().equals("500"));
    }
}
