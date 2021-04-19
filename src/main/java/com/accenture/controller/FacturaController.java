package com.accenture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.models.Factura;
import com.accenture.service.FacturaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/pagos/facturas")
public class FacturaController {
	@Autowired
	FacturaService facturaService; 
	@GetMapping
	ResponseEntity<List<Factura>> getAllFacturas() {
		return ResponseEntity.ok().body(facturaService.listaFacturas());
	}
}
