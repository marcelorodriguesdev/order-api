package br.com.btg.order.infra.database.repository;

import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    List<PedidoModel> findAllByCliente(CustomerModel customerModel);

}
