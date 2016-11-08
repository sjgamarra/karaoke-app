package com.karaoke.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.repository.CancionRepository;

@RestController
public class CancionController {
	@Autowired
    private CancionRepository cancionRepository;
	
	@RequestMapping(value = "/cancion", method = RequestMethod.POST)
	public void crearCancion(@RequestBody Cancion cancion){
		cancionRepository.save(cancion);
	}
	
//	@RequestMapping(value = "/canciones", method = RequestMethod.POST)
//	public void crearCancionDesdeLista(@RequestBody List<Cancion> cancionLst){
//		//guardar lista de canciones
//		cancionLst.stream().forEach(c -> cancionRepository.save(c));
//	}
	
	
	
}
