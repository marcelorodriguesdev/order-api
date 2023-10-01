package br.com.btg.desafioengenharia.service;

import br.com.btg.desafioengenharia.exception.NotFoundException;
import br.com.btg.desafioengenharia.model.ClienteModel;
import br.com.btg.desafioengenharia.model.ItemPedidoModel;
import br.com.btg.desafioengenharia.model.PedidoModel;
import br.com.btg.desafioengenharia.model.ProdutoModel;
import br.com.btg.desafioengenharia.repository.ClienteRepository;
import br.com.btg.desafioengenharia.repository.ItemPedidoRepository;
import br.com.btg.desafioengenharia.repository.PedidoRepository;
import br.com.btg.desafioengenharia.repository.ProdutoRepository;
import br.com.btg.desafioengenharia.request.ItemRequest;
import br.com.btg.desafioengenharia.request.PedidoRequest;
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
        ClienteModel clienteModel = getClienteModel(payload);
        PedidoModel pedidoModel = salvarPedido(payload, clienteModel);
        salvarItensModel(payload, pedidoModel);
    }

    private ClienteModel getClienteModel(PedidoRequest payload) {
        return clienteRepository.findById(payload.getCodigoCliente()).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    private PedidoModel salvarPedido(PedidoRequest payload, ClienteModel clienteModel) {
        return pedidoRepository.save(getPedidoModel(payload, clienteModel));
    }

    private void salvarItensModel(PedidoRequest payload, PedidoModel pedidoModel) {
        payload.getItens().forEach(itemRequest -> {
            ItemPedidoModel itemPedidoModel = ItemPedidoModel.builder()
                    .quantidade((int) itemRequest.getQuantidade())
                    .pedido(pedidoModel)
                    .produto(getProdutoModel(itemRequest))
                    .build();
            itemPedidoRepository.save(itemPedidoModel);
        });
    }

    private PedidoModel getPedidoModel(PedidoRequest payload, ClienteModel clienteModel) {
        return PedidoModel.builder()
                .codigoPedido(payload.getCodigoPedido())
                .cliente(clienteModel)
                .dataHoraPedido(LocalDateTime.now())
                .build();
    }

    private ProdutoModel getProdutoModel(ItemRequest itemRequest) {
        return produtoRepository.findByNomeIgnoreCase(itemRequest.getProduto()).orElseThrow(() -> new NotFoundException("Produto não localizado"));
    }

}
