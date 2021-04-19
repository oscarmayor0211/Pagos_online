package com.accenture.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Pedidos")
public class Pedido {

	public static final String NOMBRE_EMPRESA = "ACCENTURE";
	public static final Double VALOR_DOMICILIO = 30000.0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaPedido;

	private Double servicioDomicilio;

	private Double costoPedido;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@OneToMany
	@JoinColumn(name = "id_pedido")
	@JsonIgnore
	private List<Factura> facturas;

	public Pedido() {
		this.facturas = new ArrayList<>();
	}

	public void agregarFactura(Factura factura) {
		this.facturas.add(factura);
	}

	public Factura obtenerFactura(Long id) {
		for (int i = 0; i < facturas.size(); i++) {
			if (facturas.get(i).getId() == id) {
				return facturas.get(i);
			}
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Double getServicioDomicilio() {
		return servicioDomicilio;
	}

	public void setServicioDomicilio(Double servicioDomicilio) {
		this.servicioDomicilio = servicioDomicilio;
	}

	public Double getCostoPedido() {
		return costoPedido;
	}

	public void setCostoPedido(Double costoPedido) {
		this.costoPedido = costoPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

}
