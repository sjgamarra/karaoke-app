package com.karaoke.domain.dao.impl;

import java.util.List;

import javax.persistence.Query;

//import org.springframework.stereotype.Component;

import com.karaoke.domain.dao.CancionDao;
import com.karaoke.domain.entity.Cancion;

//@Component("cancionDao")
public class CancionDaoImpl extends GenericDaoImpl<Cancion> implements CancionDao{
//	    public Cancion retrieveByNombre(String nombre) {
//	        Query query = this.em
//	                .createQuery("select c FROM Cancion c where c.nombre= :nombre");
//	        query.setParameter("nombre", nombre);
//	        List<Cancion> cancionesLst = query.getResultList();
//	        if (cancionesLst != null && cancionesLst.size() == 1) {
//	            return cancionesLst.get(0);
//	        }
//	        return null;
//	    }
}
