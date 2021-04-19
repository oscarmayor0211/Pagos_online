package com.accenture.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.models.Pedido;
import com.accenture.service.PedidoService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/pagos/pedidos")
public class PedidoController {
	@Autowired
	PedidoService pedidoService;


	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	/**
	 * 
	 * @param pedido     objeto Pedido.
	 * @param idProducto ids de los objetos Producto pasados por la url.
	 * @param cantidad   cantidades de los objetos Producto pasados por la url.
	 * @return el objeto model.
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido, @RequestParam(value = "ids") Long[] idProducto,
			@RequestParam(value = "cantidad") Integer[] cantidad) {

		Map<String, Object> model = new LinkedHashMap<>();
		try {
			model = pedidoService.generarFactura(pedido, idProducto, cantidad, model);
		} catch (DataAccessException e) {
			model.put("mensaje", e.getMostSpecificCause().getMessage());
		}
		System.out.println(model);
		return new ResponseEntity<>(model, HttpStatus.OK);
	}

	/**
	 *
	 * @param pedido
	 * @param idProducto
	 * @param cantidad
	 * @param model
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Map<String, Object>> actualizarPedido(@RequestBody Pedido pedido,
			@RequestParam Long[] idProducto, @RequestParam Integer[] cantidad, Map<String, Object> model) {
		try {
			model = pedidoService.actualizarFactura(pedido, idProducto, cantidad);
		} catch (DataAccessException e) {
			model.put("mensaje", "Ocurrio un error de actualizacion");
			model.put("error", e.getMostSpecificCause().getMessage());
		}

		return new ResponseEntity<>(model, HttpStatus.OK);
	}

	@GetMapping
	public List<Pedido> obtenerPedidos() {
		return pedidoService.listaPedidos();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarPedido(@PathVariable Long id, Map<String, Object> model) {
		model = pedidoService.borrarPedidoPorId(id, model);
		return new ResponseEntity<>(model, HttpStatus.OK);
	}
}
