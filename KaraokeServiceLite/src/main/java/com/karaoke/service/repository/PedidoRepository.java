package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

	//SERGIO
	//Obtener Pedidos Pendientes.
	//(Este metodo deberia devolver la lista de reproduccion).
	public List<Pedido> findByEstado(int estado);
	
	//Obtener Pedidos Pendientes por Dispositivo. 	
	public List<Pedido> findByDispositivoIdAndEstado(String dispositivoId, int estado);
	
	//Obtener Pedio por Dispositivo Cancion y Estado
	//public Pedido findByDispositivoIdAndCancion(String dispositivoId, Cancion cancion);
	
	//Obtener Pedido por Id
	public Pedido findById(Long id);
	
	//LUIS
	
	//@Query(value = "select p from Pedido p where p.estado=?1 order by p.id asc")
	public Pedido findFirstByEstadoAndDispositivoIdNotOrderByIdAsc(int estado,String dispositivoId);
	
	public Pedido findFirstByEstadoAndDispositivoIdOrderByIdAsc(int estado,String dispositivoId);
	
	public Pedido findFirstByEstadoOrderByIdAsc(int estado);
	
	public Pedido findFirstByEstado(int estado);
	
	
	public Pedido findByDispositivoIdAndCancion(String dispositivoId, Cancion cancion);
	
	//delete all
	@Modifying
	@Transactional
	@Query("DELETE FROM Pedido p WHERE p.estado=3 or p.estado=4")
	public void clearPedidos();
	
}
