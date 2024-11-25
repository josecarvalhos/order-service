package br.com.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.orderservice.model.Pedidos;

@Repository
public interface PedidoRepository extends JpaRepository<Pedidos, Long> {

	boolean existsByNumero(String numero);
}
