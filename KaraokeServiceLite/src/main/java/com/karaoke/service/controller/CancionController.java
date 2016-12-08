package com.karaoke.service.controller;

import java.net.URLDecoder;
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
	 * @param genero
	 * @param nombre
	 * @return
	 */
	@RequestMapping(value = "/canciones/{genero}/{nombre}", method = RequestMethod.GET)
	public List<Song> buscarCancion(
			@PathVariable("genero") String genero,
			@PathVariable("nombre") String nombre){
	
		List<Song> songs = new ArrayList<Song>();
		
		try{
			genero = genero.equals("all")?"":URLDecoder.decode(genero, "UTF-8");
			nombre = nombre.equals("all")?"": URLDecoder.decode(nombre, "UTF-8");
			
			System.out.println("parametros:"+genero+":"+nombre);
			
			List<Cancion> canciones = (List<Cancion>) cancionRepository.findByGeneroContainingIgnoreCaseAndTituloContainingIgnoreCaseOrArtistaContainingIgnoreCase(genero, nombre, nombre);
			
			for(Cancion cancion : canciones){
				Song song = new Song(
						cancion.getId(),
						cancion.getTitulo(), 
						cancion.getArtista(), 
						cancion.getGenero(), 
						cancion.getEstado());
				songs.add(song);
			}
		}catch(Exception e){
			//log
		}
		
		return songs;
	}
	
	
}
