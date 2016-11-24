package com.karaoke.service.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;
import com.karaoke.service.repository.PedidoRepository;

@RestController
public class PedidoController {
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	@RequestMapping(value = "/pedido/{dispositivoId}/{cancionId}", method = RequestMethod.POST)
	public void crearPedido(@PathVariable("dispositivoId") String dispositivoId, @PathVariable("cancionId") long cancionId){
		
		System.out.println("PedidoController - crearPedido");
		
		Cancion cancion = new Cancion();
		cancion.setId(cancionId);
		
		Pedido pedido = new Pedido();
		pedido.setDispositivoId(dispositivoId);
		pedido.setCancion(cancion);
		pedido.setEstado(1);
		pedido.setFechaHora(new Date());
		
		pedidoRepository.save(pedido);
	}

}
