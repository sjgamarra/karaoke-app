package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Parametro;

public interface CancionRepository extends CrudRepository<Cancion, Long>{
	List<Cancion> findByNombre(String nombre);
}
