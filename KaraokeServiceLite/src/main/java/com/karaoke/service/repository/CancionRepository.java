package com.karaoke.service.repository;

import org.springframework.data.repository.CrudRepository;

import com.karaoke.service.entity.Cancion;

public interface CancionRepository extends CrudRepository<Cancion, Long>{

}
