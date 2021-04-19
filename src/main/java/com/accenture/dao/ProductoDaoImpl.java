package com.accenture.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.models.Producto;
import com.accenture.repository.ProductoRepository;
@Service
public class ProductoDaoImpl implements ProductoDao{

	@Autowired
	ProductoRepository productoRepo;
	@Override
	public Producto createProducto(Producto producto) {
		// TODO Auto-generated method stub
		return productoRepo.save(producto);
	}

	@Override
	public List<Producto> listarProductos() {
		// TODO Auto-generated method stub
		return productoRepo.findAll();
	}

}
