package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.karaoke.service.entity.Cancion;

public interface CancionRepository extends CrudRepository<Cancion, Long>{

	List<Cancion> findByNombreArchivo(String nombre);
	//List<Cancion> findByNombre(String nombre);
	List<Cancion> findByTituloContaining(String nombre);
	//List<Cancion> findByGeneroAndNombreLike(String genero, String nombre);

}
