package com.accenture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
