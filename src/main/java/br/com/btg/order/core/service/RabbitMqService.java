package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.ItemPedidoModel;
import br.com.btg.order.infra.database.model.PedidoModel;
import br.com.btg.order.infra.database.model.ProdutoModel;
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
        PedidoModel pedidoModel = salvarPedido(payload, customerModel);
        salvarItensModel(payload, pedidoModel);
    }

    private CustomerModel getClienteModel(PedidoRequest payload) {
        return clienteRepository.findById(payload.getCodigoCliente()).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    private PedidoModel salvarPedido(PedidoRequest payload, CustomerModel customerModel) {
        return pedidoRepository.save(getPedidoModel(payload, customerModel));
    }

    private void salvarItensModel(PedidoRequest payload, PedidoModel pedidoModel) {
        payload.getItens().forEach(itemRequest -> {
            ItemPedidoModel itemPedidoModel = ItemPedidoModel.builder()
                    .quantity((int) itemRequest.getQuantidade())
                    .order(pedidoModel)
                    .product(getProdutoModel(itemRequest))
                    .build();
            itemPedidoRepository.save(itemPedidoModel);
        });
    }

    private PedidoModel getPedidoModel(PedidoRequest payload, CustomerModel customerModel) {
        return PedidoModel.builder()
                .codigoPedido(payload.getCodigoPedido())
                .customer(customerModel)
                .dataHoraPedido(LocalDateTime.now())
                .build();
    }

    private ProdutoModel getProdutoModel(ItemRequest itemRequest) {
        return produtoRepository.findByNomeIgnoreCase(itemRequest.getProduto()).orElseThrow(() -> new NotFoundException("Produto não localizado"));
    }

}
