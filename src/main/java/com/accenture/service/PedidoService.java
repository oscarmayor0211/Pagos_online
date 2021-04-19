package com.accenture.service;

import java.util.List;
import java.util.Map;

import com.accenture.models.Pedido;

public interface PedidoService {
	 Map<String, Object> generarFactura(Pedido pedido, Long[] idProductos, Integer[] cantidad, Map<String, Object> model);

	    Map<String, Object> actualizarFactura(Pedido pedido, Long[] idProductos, Integer[] cantidad);

	    List<Pedido> listaPedidos();

	    Map<String, Object> borrarPedidoPorId(Long id, Map<String, Object> model);
}
