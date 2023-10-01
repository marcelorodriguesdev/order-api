package br.com.btg.desafioengenharia.repository;

import br.com.btg.desafioengenharia.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    Optional<ProdutoModel> findByNomeIgnoreCase(String nome);

}
