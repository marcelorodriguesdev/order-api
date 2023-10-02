package br.com.btg.order.api.controller;

import br.com.btg.order.api.response.CustomerOrderQuantityResponse;
import br.com.btg.order.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/{customerId}/orders_quantity")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrderQuantityResponse getOrderQuantityForCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getOrderQuantityForCustomerById(customerId);
    }
}
