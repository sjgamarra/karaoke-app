package com.karaoke.domain.test;

//import org.springframework.beans.factory.annotation.Autowired;

import com.karaoke.domain.dao.CancionDao;
import com.karaoke.domain.dao.impl.CancionDaoImpl;
import com.karaoke.domain.entity.Cancion;

public class CancionDaoTest {
//	@Autowired
//	private static CancionDao cancionDao;
//	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Cancion cancion = new Cancion();
		cancion.setNombre("TestNombre5");
		cancion.setAutor("TestAutor5");
		cancion.setGenero("TestGenero5");
		cancion.setEstado(1);
		//cancion.setId(2L);
		
		CancionDao cancionDao = new CancionDaoImpl();
		cancionDao.create(cancion);
	}

}
