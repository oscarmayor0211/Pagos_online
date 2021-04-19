package com.accenture.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = " no debe estar vacio")
	private String nombre;

	@NotEmpty(message = " no puede estar vacio")
	private String apellido;

	@NotEmpty(message = " no puede estar vacio")
	private String cedula;

	@NotEmpty(message = " no puede estar vacio")
	private String direccion;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@CreationTimestamp
	private LocalDate creadoEn;

	@OneToMany(mappedBy = "cliente")
	private List<Factura> factura;

	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedido;

	public Cliente() {
		this.pedido = new ArrayList<>();
	}

	@PrePersist
	public void prepersist() {
		this.creadoEn = LocalDate.now();
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalDate getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDate creadoEn) {
		this.creadoEn = creadoEn;
	}

	public List<Factura> getFactura() {
		return factura;
	}

	public void setFactura(List<Factura> factura) {
		this.factura = factura;
	}

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	
}
