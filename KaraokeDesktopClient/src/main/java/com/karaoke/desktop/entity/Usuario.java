package com.karaoke.desktop.entity;

import java.io.Serializable;


/**
 * The persistent class for the "Usuario" database table.
 * 
 */
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String contrasenia;

	private Integer estado;

	private String nombre;

	public Usuario() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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

}