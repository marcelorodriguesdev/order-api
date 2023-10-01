package br.com.btg.order.core.service;

import br.com.btg.order.core.domain.NotFoundException;
import br.com.btg.order.api.utils.mapper.PedidoMapper;
import br.com.btg.order.infra.database.model.ClienteModel;
import br.com.btg.order.infra.database.model.PedidoModel;
import br.com.btg.order.infra.database.repository.PedidoRepository;
import br.com.btg.order.api.response.PedidoResponse;
import br.com.btg.order.api.response.PedidosClienteResponse;
import br.com.btg.order.api.response.ValorTotalPedidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public PedidoResponse getPedidoPorId(Long id) {
        PedidoModel pedidoModel = pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não localizado"));
        PedidoResponse pedidoResponse = pedidoMapper.modelToResponse(pedidoModel);
        return pedidoResponse;
    }

    public ValorTotalPedidoResponse getValorTotalPedido(Long id) {
        PedidoModel pedidoModel = pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não localizado"));

        BigDecimal valorTotal = BigDecimal.valueOf(pedidoModel.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum()).setScale(2, RoundingMode.CEILING);

        return ValorTotalPedidoResponse.builder()
                .pedido(id)
                .valorTotal(valorTotal)
                .build();
    }

    public PedidosClienteResponse getPedidosResponseModelPorCliente(ClienteModel clienteModel) {
        List<PedidoResponse> pedidosResponse = new ArrayList<>();
        List<PedidoModel> pedidosModelPorCliente = getPedidosModelPorCliente(clienteModel);
        pedidosModelPorCliente.forEach(pedido -> {
            PedidoResponse pedidoResponse = pedidoMapper.modelToResponse(pedido);
            pedidosResponse.add(pedidoResponse);
        });
        return PedidosClienteResponse.builder()
                .idCliente(clienteModel.getId())
                .nomeCliente(clienteModel.getName())
                .pedidos(pedidosResponse)
                .build();
    }

    public List<PedidoModel> getPedidosModelPorCliente(ClienteModel clienteModel) {
        return pedidoRepository.findAllByCliente(clienteModel);
    }

}
