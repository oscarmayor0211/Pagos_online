package com.accenture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.models.Cliente;
import com.accenture.service.ClienteService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/pagos/clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping
	ResponseEntity<List<Cliente>> getAllClientes() {
		return ResponseEntity.ok().body(clienteService.listarClientes());
	}

	@GetMapping("/{id}")
	ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
		return ResponseEntity.ok().body(clienteService.getClienteById(id));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		return ResponseEntity.ok().body(this.clienteService.createCliente(cliente));
	}

	@PutMapping("/{id}/edit")
	ResponseEntity<Cliente> updateCliente(@PathVariable int id, @RequestBody Cliente cliente) {
		cliente.setId(id);
		return ResponseEntity.ok().body(this.clienteService.updateProject(cliente));
	}

	@DeleteMapping("/{id}/delete")
	ResponseEntity<?> deleteCliente(@PathVariable int id) {
		this.clienteService.eliminarCliente(id);
		return ResponseEntity.noContent().build();
	}
}
