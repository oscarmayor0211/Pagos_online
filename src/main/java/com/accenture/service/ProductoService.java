package com.accenture.service;

import java.util.List;

import com.accenture.models.Producto;

public interface ProductoService {
    Producto createProducto(Producto producto);
	
    List<Producto> listarProductos();


}
