package br.com.btg.order.infra.database.repository;

import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findAllByCliente(CustomerModel customerModel);

}
