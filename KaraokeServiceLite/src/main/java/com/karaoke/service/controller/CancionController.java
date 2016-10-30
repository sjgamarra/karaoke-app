package com.karaoke.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.repository.CancionRepository;

@RestController
public class CancionController {
	@Autowired
    private CancionRepository cancionRepository;
	
	//@RequestMapping("/cancion")
	@PostMapping("/cancion")
	public void crearCancion(
				@RequestParam(value="nombre") String nombre,
				@RequestParam(value="autor") String autor,
				@RequestParam(value="genero") String genero,
				@RequestParam(value="name", defaultValue="1") Integer estado
			){
		Cancion cancion = new Cancion();
		cancion.setNombre(nombre);
		cancion.setAutor(autor);
		cancion.setGenero(genero);
		cancion.setEstado(estado);
		cancionRepository.save(cancion);
	}
}
