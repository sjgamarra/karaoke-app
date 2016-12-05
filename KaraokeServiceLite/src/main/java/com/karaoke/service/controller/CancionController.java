package com.karaoke.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Song;
import com.karaoke.service.repository.CancionRepository;

@RestController
public class CancionController {
	@Autowired
    private CancionRepository cancionRepository;
	
	@RequestMapping(value = "/cancion", method = RequestMethod.POST)
	public void crearCancion(@RequestBody Cancion cancion){
		cancionRepository.save(cancion);
	}
	

	@RequestMapping(value = "/cancion/{genero}/{nombre}", method = RequestMethod.GET)
	public List<Song> buscarCancion(
			@PathVariable("nombre") String genre,
			@PathVariable("nombre") String name){
		
		//deberia hacerse en 1 sola consulta
		List<Cancion> canciones = (List<Cancion>) cancionRepository.findByTituloContaining(name);
		
		List<Song> songs = new ArrayList<Song>();
		for(Cancion cancion : canciones){
			System.out.println(cancion.getTitulo());
			Song song = new Song(
					cancion.getId(),
					cancion.getTitulo(), 
					cancion.getArtista(), 
					cancion.getGenero(), 
					cancion.getEstado());
			songs.add(song);
		}
		
		return songs;

	}
}
