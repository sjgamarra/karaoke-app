package com.karaoke.service.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		Cancion original = cancionRepository.findOne(cancion.getId());
		
		if(!cancion.getArtista().isEmpty())
			original.setArtista(cancion.getArtista());
		
		if(!cancion.getTitulo().isEmpty())
			original.setTitulo(cancion.getTitulo());
		
		if(!cancion.getGenero().isEmpty())
			original.setGenero(cancion.getGenero());
				
		cancionRepository.save(original);
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
			genero = genero.equals("all")?"": URLDecoder.decode(genero, "UTF-8");
			nombre = nombre.equals("all")?"": URLDecoder.decode(nombre, "UTF-8");
			
			List<Cancion> canciones = new ArrayList<Cancion>();
			if(genero.isEmpty() && nombre.isEmpty()){
				canciones = (List<Cancion>) cancionRepository.findTop1000ByOrderByIdAsc();
			}else{
				canciones = (List<Cancion>) cancionRepository.findCancion(genero, nombre, nombre);
			}
						
			//List<Cancion> canciones = (List<Cancion>) cancionRepository.findCancion(genero, nombre, nombre);
			
			for(Cancion cancion : canciones){
				Song song = new Song(
						cancion.getId(),
						cancion.getTitulo(), 
						cancion.getArtista(), 
						cancion.getGenero(), 
						cancion.getEstado(),
						0l,	//sin id de pedido
						""	//sin id de dispositivo
						);
				songs.add(song);
			}
		}catch(Exception e){
			//log
		}
		
		return songs;
	}
	
	@RequestMapping(value = "/generos", method = RequestMethod.GET)
	public List<String> obtenerGeneros(){
	
		List<String> generos = new ArrayList<String>();
		
		try{
			generos = cancionRepository.findGeneros();
		}catch(Exception e){
			//log
		}
		
		return generos;
	}
	
	
	@RequestMapping(value = "/canciones", method = RequestMethod.GET)
	public List<Cancion> buscarCancionWeb(
			@RequestParam("artista") String artista,
			@RequestParam("titulo") String titulo,
			@RequestParam("genero") String genero
			){
		
		System.out.println("BuscarCancionWeb:"+artista+":"+titulo+":"+genero);
		
		List<Cancion> canciones = new ArrayList<Cancion>();
	
		try{
			canciones = cancionRepository.findByGeneroContainingIgnoreCaseAndTituloContainingIgnoreCaseAndArtistaContainingIgnoreCase(genero, titulo, artista);
		}catch(Exception e){
			//log
		}
		
		return canciones;
	}
	
	
}
