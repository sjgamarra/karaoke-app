package com.karaoke.desktop.entity;

import java.io.Serializable;


/**
 * The persistent class for the "Parametro" database table.
 * 
 */
public class Parametro implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String descripcion;

	private Integer estado;

	private String nombre;

	private String valor;

	public Parametro() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}