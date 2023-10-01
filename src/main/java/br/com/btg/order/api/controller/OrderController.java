package br.com.btg.order.api.controller;

import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.api.response.CustomerOrdersResponse;
import br.com.btg.order.api.response.TotalOrderAmountResponse;
import br.com.btg.order.core.service.CustomerService;
import br.com.btg.order.core.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static br.com.btg.order.api.response.ErrorResponse.ERROR_RESPONSE_EXCEPTION_MODEL;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/{id}/total_amount")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Total amount returned successfully."),
            @ApiResponse(code = 404, message = "Order not found.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Internal server error,", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public TotalOrderAmountResponse getTotalOrderValue(@PathVariable("id") Long id) {
        return orderService.getTotalOrderValue(id);
    }

    @GetMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orders returned successfully."),
            @ApiResponse(code = 404, message = "Customer not found.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Internal server error.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    })
    public CustomerOrdersResponse getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        CustomerModel customerById = customerService.getCustomerById(customerId);

        return orderService.getOrdersByCustomer(customerById);
    }

}
