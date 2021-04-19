package com.accenture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.models.Producto;
import com.accenture.service.ProductoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/pagos/productos")
public class ProductoController {

	@Autowired
	ProductoService productoService;
	
	@GetMapping
	ResponseEntity<List<Producto>> getAllProductos() {
		return ResponseEntity.ok().body(productoService.listarProductos());
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Producto> createCliente(@RequestBody Producto producto) {
		return ResponseEntity.ok().body(this.productoService.createProducto(producto));
	}

}
