package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.repository.ClienteRepository;
import br.com.btg.order.api.request.ClienteRequest;
import br.com.btg.order.api.response.ClienteResponse;
import br.com.btg.order.api.response.CustomerOrderQuantityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrderService orderService;

    public CustomerModel getCustomerById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    public CustomerOrderQuantityResponse getOrderQuantityForCustomerById(Long id) {
        CustomerModel customerModel = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
        int quantidadeDePedidos = orderService.getPedidosModelPorCliente(customerModel).size();
        return CustomerOrderQuantityResponse.builder()
                .idCLiente(id)
                .nomeCLiente(customerModel.getName())
                .quantidadePedidos(quantidadeDePedidos)
                .build();
    }

    public ClienteResponse salvarCliente(ClienteRequest clienteRequest) {
        CustomerModel customerModel = clienteRepository.save(CustomerModel.builder()
                .name(clienteRequest.getNome())
                .build());
        return ClienteResponse.builder()
                .idCliente(customerModel.getId())
                .nomeCliente(customerModel.getName())
                .mensagem("Cliente salvo com sucesso")
                .build();
    }

}
