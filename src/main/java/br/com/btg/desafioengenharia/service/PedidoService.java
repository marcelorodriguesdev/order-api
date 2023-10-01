package br.com.btg.desafioengenharia.service;

import br.com.btg.desafioengenharia.exception.NotFoundException;
import br.com.btg.desafioengenharia.mapper.PedidoMapper;
import br.com.btg.desafioengenharia.model.ClienteModel;
import br.com.btg.desafioengenharia.model.PedidoModel;
import br.com.btg.desafioengenharia.repository.PedidoRepository;
import br.com.btg.desafioengenharia.response.PedidoResponse;
import br.com.btg.desafioengenharia.response.PedidosClienteResponse;
import br.com.btg.desafioengenharia.response.ValorTotalPedidoResponse;
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

        BigDecimal valorTotal = BigDecimal.valueOf(pedidoModel.getItens().stream()
                .mapToDouble(item -> item.getQuantidade() * item.getProduto().getPreco())
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
                .nomeCliente(clienteModel.getNome())
                .pedidos(pedidosResponse)
                .build();
    }

    public List<PedidoModel> getPedidosModelPorCliente(ClienteModel clienteModel) {
        return pedidoRepository.findAllByCliente(clienteModel);
    }

}
