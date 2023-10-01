package br.com.btg.desafioengenharia.service;

import br.com.btg.desafioengenharia.exception.NotFoundException;
import br.com.btg.desafioengenharia.model.ClienteModel;
import br.com.btg.desafioengenharia.repository.ClienteRepository;
import br.com.btg.desafioengenharia.request.ClienteRequest;
import br.com.btg.desafioengenharia.response.ClienteResponse;
import br.com.btg.desafioengenharia.response.QuantidadePedidosClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoService pedidoService;

    public ClienteModel getClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
    }

    public QuantidadePedidosClienteResponse getQuantidadePedidosClientePorId(Long id) {
        ClienteModel clienteModel = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não localizado"));
        int quantidadeDePedidos = pedidoService.getPedidosModelPorCliente(clienteModel).size();
        return QuantidadePedidosClienteResponse.builder()
                .idCLiente(id)
                .nomeCLiente(clienteModel.getNome())
                .quantidadePedidos(quantidadeDePedidos)
                .build();
    }

    public ClienteResponse salvarCliente(ClienteRequest clienteRequest) {
        ClienteModel clienteModel = clienteRepository.save(ClienteModel.builder()
                .nome(clienteRequest.getNome())
                .build());
        return ClienteResponse.builder()
                .idCliente(clienteModel.getId())
                .nomeCliente(clienteModel.getNome())
                .mensagem("Cliente salvo com sucesso")
                .build();
    }

}
