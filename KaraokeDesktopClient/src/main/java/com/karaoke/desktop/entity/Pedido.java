package com.karaoke.desktop.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the "Pedido" database table.
 * 
 */
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String dispositivoId;

	private Integer estado;

	private Date fechaHora;
	//private Timestamp fechaHora;

	//bi-directional many-to-one association to Cancion
	private Cancion cancion;

	public Pedido() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDispositivoId() {
		return this.dispositivoId;
	}

	public void setDispositivoId(String dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Cancion getCancion() {
		return this.cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

}