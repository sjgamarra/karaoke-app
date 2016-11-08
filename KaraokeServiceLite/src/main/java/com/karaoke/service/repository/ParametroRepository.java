package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.karaoke.service.entity.Parametro;

public interface ParametroRepository extends CrudRepository<Parametro, Long>{
	List<Parametro> findByNombre(String nombre);
}
