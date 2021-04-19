package com.accenture.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.models.Factura;
import com.accenture.repository.FacturaRepository;
@Service
public class FacturaDaoImpl implements FacturaDao {

	@Autowired
	FacturaRepository facturaRepo;
	
	@Override
	public List<Factura> listaFacturas() {
		// TODO Auto-generated method stub
		return facturaRepo.findAll();
	}

}
