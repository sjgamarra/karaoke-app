package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;

public interface CancionRepository extends CrudRepository<Cancion, Long>{

	List<Cancion> findByNombreArchivo(String nombre);
	//List<Cancion> findByNombre(String nombre);
	
	//Obtener canciones por Genero y Titulo y Artista
	List<Cancion> findByGeneroContainingIgnoreCaseAndTituloContainingIgnoreCaseOrArtistaContainingIgnoreCase(String genero, String nombre, String artista);
	
	//Buscar canciones por genero titulo y autor (NO BORRAR)
	@Query("SELECT c FROM Cancion c WHERE (upper(c.genero) like upper(concat('%',:genero,'%'))) and (upper(c.titulo) like upper(concat('%',:titulo,'%')) or upper(c.artista) like upper(concat('%',:artista,'%')))")
	List<Cancion> findCancion(@Param("genero")String genero, @Param("titulo")String titulo, @Param("artista")String artista);
	
	@Query("SELECT DISTINCT c.genero FROM Cancion c")
	List<String> findGeneros();
	
	
	List<Cancion> findTop100ByOrderByIdAsc();

}
