package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;

public interface CancionRepository extends CrudRepository<Cancion, Long>{

	
	List<Cancion> findByNombreArchivo(String nombre);
	//List<Cancion> findByNombre(String nombre);
	
	//Obtener canciones por Genero y Titulo 
	List<Cancion> findByGeneroContainingIgnoreCaseAndTituloContainingIgnoreCase(String genero, String nombre);
	
	List<Cancion> findTop100ByOrderByIdAsc();

}
