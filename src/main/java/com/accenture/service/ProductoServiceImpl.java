package com.accenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.dao.ProductoDao;
import com.accenture.models.Producto;
@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	ProductoDao productoDao;
	@Override
	public Producto createProducto(Producto producto) {
		// TODO Auto-generated method stub
		return productoDao.createProducto(producto);
	}

	@Override
	public List<Producto> listarProductos() {
		// TODO Auto-generated method stub
		return productoDao.listarProductos();
	}

}
