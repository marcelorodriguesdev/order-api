package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.repository.CustomerRepository;
import br.com.btg.order.api.response.CustomerOrderQuantityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;

    public CustomerModel getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found."));
    }

    public CustomerOrderQuantityResponse getOrderQuantityForCustomerById(Long id) {
        CustomerModel customerModel = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found."));
        int ordersQuantity = orderService.getOrdersByCustomerModel(customerModel).size();
        return CustomerOrderQuantityResponse.builder()
                .customerId(id)
                .customerName(customerModel.getName())
                .ordersQuantity(ordersQuantity)
                .build();
    }

}
