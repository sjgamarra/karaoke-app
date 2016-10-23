package com.karaoke.domain.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.karaoke.domain.dao.GenericDao;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericDaoImpl <T> implements GenericDao<T>{
	 	//@PersistenceContext (unitName = "KaraokeDomain")
	    protected EntityManagerFactory entityManagerFactory;;

	    private Class<T> type;

	    public GenericDaoImpl() {
	        Type t = getClass().getGenericSuperclass();
	        ParameterizedType pt = (ParameterizedType) t;
	        type = (Class) pt.getActualTypeArguments()[0];
	        
	        entityManagerFactory = Persistence.createEntityManagerFactory( "KaraokeDomain" );
	    }

//	    @Override
	    public List<T> retrieveAll() {
	    	EntityManager em = entityManagerFactory.createEntityManager();
	        StringBuffer queryString = new StringBuffer(
	                "SELECT o from o");
        	Query query = em.createQuery(queryString.toString());
        	em.close();
        	return (List<T>) query.getResultList();
        	
	    }

//	    @Override
	    public T create(final T t) {
	    	EntityManager em = entityManagerFactory.createEntityManager();
	    	em.getTransaction().begin();
    		em.persist(t);
    		em.getTransaction().commit();
    		em.close();
	    	return t;
	    }

//	    @Override
	    public void delete(final Object id) {
	    	EntityManager em = entityManagerFactory.createEntityManager();
	        em.remove(em.getReference(type, id));
	        em.close();
	    }

//	    @Override
	    public T find(final Object id) {
	    	EntityManager em = entityManagerFactory.createEntityManager();
	    	Object obj=em.find(type, id);
	    	em.close();
	        return (T) obj;
	    }

//	    @Override
	    public T update(final T t) {
	    	EntityManager em = entityManagerFactory.createEntityManager();
	    	Object obj=em.merge(t);
	    	em.close();
	        return (T) obj;    
	    }
}
