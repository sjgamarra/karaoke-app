package com.karaoke.service.impl;

import javax.jws.WebService;

import com.karaoke.domain.dao.CancionDao;
import com.karaoke.domain.dao.impl.CancionDaoImpl;
import com.karaoke.domain.entity.Cancion;
import com.karaoke.service.CancionService;

@WebService(targetNamespace = "http://localhost:8080/", endpointInterface = "com.karaoke.service.CancionService", portName = "CancionServiceImplPort", serviceName = "CancionServiceImplService")
public class CancionServiceImpl implements CancionService{
	public void crearCancion(Cancion cancion){
		CancionDao cancionDao = new CancionDaoImpl();
		cancionDao.create(cancion);
	}
}