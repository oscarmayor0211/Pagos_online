package com.accenture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	   Cliente findByCedulaAndDireccion(String cedula, String direccion);

	   Cliente findByCedula(String cedula);
}
