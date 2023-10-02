package br.com.btg.order.controller;

import br.com.btg.order.api.controller.OrderController;
import br.com.btg.order.core.domain.handler.ExceptionHandlerAdvice;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.api.response.CustomerOrdersResponse;
import br.com.btg.order.api.response.TotalOrderAmountResponse;
import br.com.btg.order.core.service.CustomerService;
import br.com.btg.order.core.service.OrderService;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {OrderController.class, ExceptionHandlerAdvice.class})
@EnableWebMvc
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldReturnOkWhenValidOrderCallsGetTotalOrderAmount() throws Exception {
        Long orderId = 1L;

        TotalOrderAmountResponse responseMock = TotalOrderAmountResponse.builder()
                .totalAmount(new BigDecimal(10.00))
                .order(orderId)
                .build();
        given(orderService.getTotalOrderValue(orderId)).willReturn(responseMock);
        MockHttpServletResponse response = mvc
                .perform(get(TestApiConstants.BASE_URL + TestApiConstants.ORDERS_PATH + orderId + TestApiConstants.TOTAL_AMOUNT_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        TotalOrderAmountResponse orderResponse = new ObjectMapper().readValue(contentAsString, TotalOrderAmountResponse.class);
        assertThat(orderResponse.getTotalAmount().equals(responseMock.getTotalAmount()));
    }

    @Test
    void shouldReturnOkWhenValidCustomerCallsGetAllOrders() throws Exception {
        Long customerId = 1L;

        CustomerOrdersResponse responseMock = CustomerOrdersResponse.builder()
                .customerName("BTG")
                .build();

        CustomerModel customerModel = CustomerModel.builder()
                .id(1L)
                .name("BTG")
                .build();

        given(customerService.getCustomerById(customerId)).willReturn(customerModel);
        given(orderService.getOrdersByCustomer(customerModel)).willReturn(responseMock);

        MockHttpServletResponse response = mvc
                .perform(get(TestApiConstants.BASE_URL + TestApiConstants.ORDERS_PATH + customerId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        CustomerOrdersResponse orderResponse = new ObjectMapper().readValue(contentAsString, CustomerOrdersResponse.class);
        assertThat(orderResponse.getCustomerName().equals(responseMock.getCustomerName()));
    }
}
