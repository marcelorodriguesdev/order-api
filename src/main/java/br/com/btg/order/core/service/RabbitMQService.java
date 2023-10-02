package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.ItemModel;
import br.com.btg.order.infra.database.model.OrderModel;
import br.com.btg.order.infra.database.model.ProductModel;
import br.com.btg.order.infra.database.repository.CustomerRepository;
import br.com.btg.order.infra.database.repository.ItemRepository;
import br.com.btg.order.infra.database.repository.OrderRepository;
import br.com.btg.order.infra.database.repository.ProductRepository;
import br.com.btg.order.api.request.ItemRequest;
import br.com.btg.order.api.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RabbitMQService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void processOrder(OrderRequest payload) {
        CustomerModel customerModel = getCustomer(payload);
        OrderModel orderModel = saveOrder(payload, customerModel);
        saveItems(payload, orderModel);
    }

    private CustomerModel getCustomer(OrderRequest payload) {
        return customerRepository.findById(payload.getCustomerId()).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    private OrderModel saveOrder(OrderRequest payload, CustomerModel customerModel) {
        return orderRepository.save(getOrder(payload, customerModel));
    }

    private void saveItems(OrderRequest payload, OrderModel orderModel) {
        payload.getItems().forEach(itemRequest -> {
            ItemModel itemModel = ItemModel.builder()
                    .quantity((int) itemRequest.getQuantity())
                    .order(orderModel)
                    .product(getProduct(itemRequest))
                    .build();
            itemRepository.save(itemModel);
        });
    }

    private OrderModel getOrder(OrderRequest payload, CustomerModel customerModel) {
        return OrderModel.builder()
                .orderId(payload.getOrderId())
                .customer(customerModel)
                .build();
    }

    private ProductModel getProduct(ItemRequest itemRequest) {
        return productRepository.findByName(itemRequest.getProduct()).orElseThrow(() -> new NotFoundException("Product not found."));
    }
}
