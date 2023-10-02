package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.repository.CustomerRepository;
import br.com.btg.order.api.request.CustomerRequest;
import br.com.btg.order.api.response.CustomerResponse;
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
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    public CustomerOrderQuantityResponse getOrderQuantityForCustomerById(Long id) {
        CustomerModel customerModel = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
        int quantidadeDePedidos = orderService.getOrdersByCustomerModel(customerModel).size();
        return CustomerOrderQuantityResponse.builder()
                .customerId(id)
                .customerName(customerModel.getName())
                .ordersQuantity(quantidadeDePedidos)
                .build();
    }

    public CustomerResponse salvarCliente(CustomerRequest customerRequest) {
        CustomerModel customerModel = customerRepository.save(CustomerModel.builder()
                .name(customerRequest.getName())
                .build());
        return CustomerResponse.builder()
                .customerId(customerModel.getId())
                .customerName(customerModel.getName())
                .message("Cliente salvo com sucesso")
                .build();
    }

}
