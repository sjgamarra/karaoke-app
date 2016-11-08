package com.karaoke.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Parametro;
import com.karaoke.service.repository.ParametroRepository;

@RestController
public class ParametroController {
	@Autowired
	private ParametroRepository parametroRepository;
	
	@RequestMapping(value = "/parametro/{nombre}", method = RequestMethod.GET)
	public Parametro crearCancion(@PathVariable("nombre") String nombre){
		Parametro param = (parametroRepository.findByNombre(nombre)!=null)?parametroRepository.findByNombre(nombre).get(0):null;
		return param;
	}

}
