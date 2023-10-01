package br.com.btg.desafioengenharia.repository;

import br.com.btg.desafioengenharia.model.ClienteModel;
import br.com.btg.desafioengenharia.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    List<PedidoModel> findAllByCliente(ClienteModel clienteModel);

}
