package com.karaoke.service;

import javax.jws.WebService;

import com.karaoke.domain.entity.Cancion;

@WebService(name = "CancionService", targetNamespace = "http://localhost:8080/")
public interface CancionService {
	public void crearCancion(Cancion cancion); 
}
