package com.accenture.dao;

import java.util.List;

import com.accenture.models.Producto;

public interface ProductoDao {
	Producto createProducto(Producto producto);

	List<Producto> listarProductos();

}
