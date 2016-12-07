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
import com.karaoke.service.entity.Pedido;
import com.karaoke.service.entity.Song;
import com.karaoke.service.repository.CancionRepository;
import com.karaoke.service.repository.PedidoRepository;

@RestController
public class CancionController {
	@Autowired
    private CancionRepository cancionRepository;
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	@RequestMapping(value = "/cancion", method = RequestMethod.POST)
	public void crearCancion(@RequestBody Cancion cancion){
		cancionRepository.save(cancion);
	}
	
	
	/***
	 * Obtener cancion en funcion a genero y nombre, ignorando mayusculas.
	 * @param genre
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/cancion/{genero}/{nombre}", method = RequestMethod.GET)
	public List<Song> buscarCancion(
			@PathVariable("genero") String genre,
			@PathVariable("nombre") String name){
	
		genre = genre.equals("all")?"":genre;
		name = name.equals("all")?"":name;
		
		List<Cancion> canciones = (List<Cancion>) cancionRepository.findByGeneroContainingIgnoreCaseAndTituloContainingIgnoreCase(genre, name);
		
		List<Song> songs = new ArrayList<Song>();
		for(Cancion cancion : canciones){
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
