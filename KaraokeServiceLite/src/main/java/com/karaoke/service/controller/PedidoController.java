package com.karaoke.service.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;
import com.karaoke.service.repository.ParametroRepository;
import com.karaoke.service.repository.PedidoRepository;

@RestController
public class PedidoController {
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	@Autowired
	private ParametroRepository parametroRepository;
	
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
	
	/**
	 *  Se consideran los siguientes estados de pedido:
	 * 1 - En Cola
	 * 2 - En Reproduccion
	 * 3 - Reproducido
	 * 4 - Cancelado
	 * @param ultimoDispositivo: Id del ultimo dispositivo que envio el pedido para tratar de obtener el pedido de otra mesa
	 * @return
	 */
	@RequestMapping(value = "/pedido", method = RequestMethod.GET)
	public Pedido obtenerPedido(String ultimoDispositivo){
		Pedido pedido = pedidoRepository.findFirstByEstadoAndDispositivoIdNotOrderByIdAsc(1,ultimoDispositivo);
		//Si no se encuentra un pedido de una mesa diferente obtenemos el siguiente pedido sin importar el id del dispositivo
		if(pedido == null)
		{
			pedido = pedidoRepository.findFirstByEstadoOrderByIdAsc(1);
		}
		
		//Si no se encuentran pedidos obtener una musica aleatoria para reproducir
		if(pedido == null)
		{
			//TODO: crear un pedido aleatorio
		}
		
		return pedido;
	}

	
	@RequestMapping(value = "/pedido", method = RequestMethod.PUT)
	public void actualizarPedido(@RequestBody Pedido pedido){
		pedidoRepository.save(pedido);
	}
}
