package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.karaoke.service.entity.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{
	
	//@Query(value = "select p from Pedido p where p.estado=?1 order by p.id asc")
	public Pedido findFirstByEstadoAndDispositivoIdNotOrderByIdAsc(int estado,String dispositivoId);
	
	public Pedido findFirstByEstadoAndDispositivoIdOrderByIdAsc(int estado,String dispositivoId);
	
	public Pedido findFirstByEstadoOrderByIdAsc(int estado);
	
	public Pedido findFirstByEstado(int estado);
	
	
}
