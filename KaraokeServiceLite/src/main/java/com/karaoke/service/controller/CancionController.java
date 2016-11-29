package com.karaoke.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.beust.jcommander.internal.Console;
import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Parametro;
import com.karaoke.service.repository.CancionRepository;

@RestController
public class CancionController {
	@Autowired
    private CancionRepository cancionRepository;
	
	@RequestMapping(value = "/cancion", method = RequestMethod.POST)
	public void crearCancion(@RequestBody Cancion cancion){
		cancionRepository.save(cancion);
	}
	
	@RequestMapping(value = "/cancion/{nombre}", method = RequestMethod.GET)
	public List<Cancion> buscarCancion(@PathVariable("nombre") String nombre){
		List<Cancion> canciones = cancionRepository.findByNombreArchivo(nombre);
		return canciones;
	}
	
	
	@RequestMapping(value = "/song/{id}", method = RequestMethod.POST)
	public void crearSong(@PathVariable("id") long id){
		System.out.println("LLEGUE AL SERVIDOR CON EL ID:"+id);
		//cancionRepository.save(cancion)0;
	}
	
	
	
//	@RequestMapping(value = "/canciones", method = RequestMethod.POST)
//	public void crearCancionDesdeLista(@RequestBody List<Cancion> cancionLst){
//		//guardar lista de canciones
//		cancionLst.stream().forEach(c -> cancionRepository.save(c));
//	}
}
