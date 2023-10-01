package br.com.btg.order.infra.database.repository;

import br.com.btg.order.infra.database.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<CustomerModel, Long> {

}
