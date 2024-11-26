package br.com.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.orderservice.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
