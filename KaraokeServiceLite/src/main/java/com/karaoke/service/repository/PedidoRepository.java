package com.karaoke.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.karaoke.service.entity.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{
	
}
