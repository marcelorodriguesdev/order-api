package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.ItemModel;
import br.com.btg.order.infra.database.model.OrderModel;
import br.com.btg.order.infra.database.model.ProductModel;
import br.com.btg.order.infra.database.repository.ClienteRepository;
import br.com.btg.order.infra.database.repository.ItemPedidoRepository;
import br.com.btg.order.infra.database.repository.PedidoRepository;
import br.com.btg.order.infra.database.repository.ProdutoRepository;
import br.com.btg.order.api.request.ItemRequest;
import br.com.btg.order.api.request.PedidoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RabbitMqService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void tratarPedido(PedidoRequest payload) {
        CustomerModel customerModel = getClienteModel(payload);
        OrderModel orderModel = salvarPedido(payload, customerModel);
        salvarItensModel(payload, orderModel);
    }

    private CustomerModel getClienteModel(PedidoRequest payload) {
        return clienteRepository.findById(payload.getCodigoCliente()).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    private OrderModel salvarPedido(PedidoRequest payload, CustomerModel customerModel) {
        return pedidoRepository.save(getPedidoModel(payload, customerModel));
    }

    private void salvarItensModel(PedidoRequest payload, OrderModel orderModel) {
        payload.getItens().forEach(itemRequest -> {
            ItemModel itemModel = ItemModel.builder()
                    .quantity((int) itemRequest.getQuantidade())
                    .order(orderModel)
                    .product(getProdutoModel(itemRequest))
                    .build();
            itemPedidoRepository.save(itemModel);
        });
    }

    private OrderModel getPedidoModel(PedidoRequest payload, CustomerModel customerModel) {
        return OrderModel.builder()
                .codigoPedido(payload.getCodigoPedido())
                .customer(customerModel)
                .dataHoraPedido(LocalDateTime.now())
                .build();
    }

    private ProductModel getProdutoModel(ItemRequest itemRequest) {
        return produtoRepository.findByNomeIgnoreCase(itemRequest.getProduto()).orElseThrow(() -> new NotFoundException("Produto não localizado"));
    }

}
