package com.accenture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
