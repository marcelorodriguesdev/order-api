package br.com.btg.order.api.controller;

import br.com.btg.order.api.response.CustomerOrderQuantityResponse;
import br.com.btg.order.core.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static br.com.btg.order.api.response.ErrorResponse.ERROR_RESPONSE_EXCEPTION_MODEL;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/{customerId}/orders_quantity")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quantity of orders returned successfully."),
            @ApiResponse(code = 404, message = "Customer not found.", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
            @ApiResponse(code = 500, message = "Internal server error", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = ERROR_RESPONSE_EXCEPTION_MODEL))),
    }
    )
    public CustomerOrderQuantityResponse getOrderQuantityForCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getOrderQuantityForCustomerById(customerId);
    }
}
