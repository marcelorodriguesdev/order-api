package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.api.utils.mapper.OrderMapper;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.OrderModel;
import br.com.btg.order.infra.database.repository.OrderRepository;
import br.com.btg.order.api.response.OrderResponse;
import br.com.btg.order.api.response.CustomerOrdersResponse;
import br.com.btg.order.api.response.TotalOrderAmountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public TotalOrderAmountResponse getTotalOrderValue(Long id) {
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não localizado"));

        BigDecimal valorTotal = BigDecimal.valueOf(orderModel.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum()).setScale(2, RoundingMode.CEILING);

        return TotalOrderAmountResponse.builder()
                .order(id)
                .totalAmount(valorTotal)
                .build();
    }

    public CustomerOrdersResponse getOrdersByCustomer(CustomerModel customerModel) {
        List<OrderResponse> ordersResponse = new ArrayList<>();
        List<OrderModel> ordersByCustomerModel = getOrdersByCustomerModel(customerModel);
        ordersByCustomerModel.forEach(pedido -> {
            OrderResponse orderResponse = orderMapper.modelToResponse(pedido);
            ordersResponse.add(orderResponse);
        });
        return CustomerOrdersResponse.builder()
                .customerId(customerModel.getId())
                .customerName(customerModel.getName())
                .orders(ordersResponse)
                .build();
    }

    public List<OrderModel> getOrdersByCustomerModel(CustomerModel customerModel) {
        return orderRepository.findAllByCliente(customerModel);
    }

}
