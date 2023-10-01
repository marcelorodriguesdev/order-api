package br.com.btg.order.infra.database.repository;

import br.com.btg.order.infra.database.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProductModel, Long> {

    Optional<ProductModel> findByNomeIgnoreCase(String nome);

}
