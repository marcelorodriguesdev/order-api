package br.com.btg.order.api.controller;

import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.api.response.CustomerOrdersResponse;
import br.com.btg.order.api.response.TotalOrderAmountResponse;
import br.com.btg.order.core.service.CustomerService;
import br.com.btg.order.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/{orderId}/total_amount")
    @ResponseStatus(HttpStatus.OK)
    public TotalOrderAmountResponse getTotalOrderValue(@PathVariable("orderId") Long orderId) {
        return orderService.getTotalOrderValue(orderId);
    }

    @GetMapping(value = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrdersResponse getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        CustomerModel customerById = customerService.getCustomerById(customerId);

        return orderService.getOrdersByCustomer(customerById);
    }

}
