package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.api.utils.mapper.PedidoMapper;
import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.PedidoModel;
import br.com.btg.order.infra.database.repository.PedidoRepository;
import br.com.btg.order.api.response.PedidoResponse;
import br.com.btg.order.api.response.CustomerOrdersResponse;
import br.com.btg.order.api.response.TotalOrderValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public PedidoResponse getPedidoPorId(Long id) {
        PedidoModel pedidoModel = pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não localizado"));
        PedidoResponse pedidoResponse = pedidoMapper.modelToResponse(pedidoModel);
        return pedidoResponse;
    }

    public TotalOrderValueResponse getTotalOrderValue(Long id) {
        PedidoModel pedidoModel = pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não localizado"));

        BigDecimal valorTotal = BigDecimal.valueOf(pedidoModel.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum()).setScale(2, RoundingMode.CEILING);

        return TotalOrderValueResponse.builder()
                .pedido(id)
                .valorTotal(valorTotal)
                .build();
    }

    public CustomerOrdersResponse getOrdersByCustomer(CustomerModel customerModel) {
        List<PedidoResponse> pedidosResponse = new ArrayList<>();
        List<PedidoModel> pedidosModelPorCliente = getPedidosModelPorCliente(customerModel);
        pedidosModelPorCliente.forEach(pedido -> {
            PedidoResponse pedidoResponse = pedidoMapper.modelToResponse(pedido);
            pedidosResponse.add(pedidoResponse);
        });
        return CustomerOrdersResponse.builder()
                .idCliente(customerModel.getId())
                .nomeCliente(customerModel.getName())
                .pedidos(pedidosResponse)
                .build();
    }

    public List<PedidoModel> getPedidosModelPorCliente(CustomerModel customerModel) {
        return pedidoRepository.findAllByCliente(customerModel);
    }

}
