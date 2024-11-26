package br.com.orderservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.orderservice.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	boolean existsByNumero(String numero);

	@EntityGraph(attributePaths = {"itens.produto"})
	Optional<Pedido> findByNumero(String numero);
}
