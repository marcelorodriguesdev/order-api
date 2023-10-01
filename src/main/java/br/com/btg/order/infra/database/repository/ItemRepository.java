package br.com.btg.order.infra.database.repository;

import br.com.btg.order.infra.database.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {

}
