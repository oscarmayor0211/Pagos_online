package com.accenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.dao.FacturaDao;
import com.accenture.models.Factura;
@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	FacturaDao facturaDao;
	@Override
	public List<Factura> listaFacturas() {
		// TODO Auto-generated method stub
		return facturaDao.listaFacturas();
	}

}
