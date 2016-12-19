package com.karaoke.service.dto;

import java.io.Serializable;
import java.util.List;

public class RespuestaBatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int numCancionesAgregados;
	private int numCancionesError;
	private String[] cancionesConError;
	private String mensajeError;
	
	public RespuestaBatchDTO(){
		super();
	}
	
	public int getNumCancionesAgregados() {
		return numCancionesAgregados;
	}
	public void setNumCancionesAgregados(int numCancionesAgregados) {
		this.numCancionesAgregados = numCancionesAgregados;
	}
	
	public String[] getCancionesConError() {
		return cancionesConError;
	}

	public void setCancionesConError(String[] cancionesConError) {
		this.cancionesConError = cancionesConError;
	}

	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public int getNumCancionesError() {
		return numCancionesError;
	}

	public void setNumCancionesError(int numCancionesError) {
		this.numCancionesError = numCancionesError;
	}
	
	
}
