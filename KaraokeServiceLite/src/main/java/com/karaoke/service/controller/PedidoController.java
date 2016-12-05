package com.karaoke.service.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;
import com.karaoke.service.repository.CancionRepository;
import com.karaoke.service.entity.Song;
import com.karaoke.service.repository.ParametroRepository;
import com.karaoke.service.repository.PedidoRepository;
import com.karaoke.service.utils.EstadoPedido;
import com.karaoke.service.utils.KaraokeUtils;
import com.karaoke.service.utils.PedidoManager;

@RestController
public class PedidoController {
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	@Autowired
    private CancionRepository cancionRepository;
	
	@Autowired
	private ParametroRepository parametroRepository;
	
	/***
	 * Obtiene pedidos en funcion al dispositvo.
	 * ALL - Busca todos los pedidos pendientes.
	 * DIS - Busca los pedidos pendientes del dispositivo.
	 * @param dispositivoId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/pedidos/{dispositivoId}", method = RequestMethod.GET)
	public List<Song> obtenerPedidos(
			@PathVariable("dispositivoId") String dispositivoId){
		
		System.out.println("PedidosController - obtenerPedidos");
		
		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		if(dispositivoId.equals("all")){
			//aca deberia obtener la lista armada de la sala.
			pedidos = (List<Pedido>) pedidoRepository.findByEstado(1);
		}else{
			pedidos = (List<Pedido>) pedidoRepository.findByDispositivoIdAndEstado(dispositivoId, 1);
		}
		
		List<Song> songs = new ArrayList<Song>();
		for(Pedido pedido : pedidos){
			Song song = new Song(
					pedido.getCancion().getId(),
					pedido.getCancion().getTitulo(), 
					pedido.getCancion().getArtista(), 
					pedido.getCancion().getGenero(), 
					pedido.getCancion().getEstado());
			songs.add(song);
		}
		
		return songs;
	}
	
	@RequestMapping(value = "/pedidos/{dispositivoId}/{cancionId}", method = RequestMethod.POST)
	public void cancelarPedidos(
			@PathVariable("dispositivoId") String dispositivoId,
			@PathVariable("cancionId") long cancionId){
		
		System.out.println("PedidosController - cancelarPedido");
		
		Cancion cancion = new Cancion();
		cancion.setId(cancionId);
		Pedido pedido = pedidoRepository.findByDispositivoIdAndCancion(dispositivoId, cancion);
		
		System.out.println("Pedido Id:"+pedido.getId());
		
		pedido.setEstado(4);
		pedidoRepository.save(pedido);
	}
	
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
	public Pedido obtenerPedido(){
		//Pedido pedido = obtenerPedidosSurtidosEnOrden();
		Pedido pedido = obtenerPedidoCircularmente();
		
		//Si no se encuentran pedidos obtener una musica aleatoria para reproducir
		if(pedido == null)
		{
			//TODO: crear un pedido aleatorio
			List<Cancion> lstCancion =  cancionRepository.findTop100ByOrderByIdAsc();
			int posRandon = (int)Math.random()*(lstCancion.size()-1);
			Cancion cancionRandom = lstCancion.get(posRandon);
			pedido = new Pedido();
			pedido.setCancion(cancionRandom);
		}
		
		return pedido;
	}

	private Pedido obtenerPedidoCircularmente(){
		Pedido pedido = null;
		
		PedidoManager pedidoManager = PedidoManager.getInstance();
		for(int i = 0 ; i < pedidoManager.getNumDispositivos(); i++){
			String dispositivoId = pedidoManager.getNextDispositivo();
			pedido = pedidoRepository.findFirstByEstadoAndDispositivoIdOrderByIdAsc(EstadoPedido.EN_COLA, dispositivoId);
			if(pedido != null)	break;
		}
		
		return pedido;
	}
	
	private Pedido obtenerPedidosSurtidosEnOrden(){
		//obtenemos el pedido anterior para luego obtener un pedido que sea de otra mesa
		Pedido pedidoAnterior = pedidoRepository.findFirstByEstado(EstadoPedido.EN_REPRODUCCION);
		Pedido pedido = null;
		if(pedidoAnterior!=null)
		{
			pedido = pedidoRepository.findFirstByEstadoAndDispositivoIdNotOrderByIdAsc(EstadoPedido.EN_COLA,pedidoAnterior.getDispositivoId());
		}
		//Si no se encuentra un pedido de una mesa diferente obtenemos el siguiente pedido sin importar el id del dispositivo
		if(pedido == null)
		{
			pedido = pedidoRepository.findFirstByEstadoOrderByIdAsc(EstadoPedido.EN_COLA);
		}
		
		return pedido;
	}
	
	@RequestMapping(value = "/pedido", method = RequestMethod.PUT)
	//public void actualizarPedido(@RequestParam  ("pedidoString") Pedido pedido){
	public void actualizarPedido(@RequestBody  Pedido pedido){
		//no actualizar en caso el pedido no tenga un dispositivoId Asociado (Pedido Random)
		if(!KaraokeUtils.isEmpty(pedido.getDispositivoId()))
			pedidoRepository.save(pedido);
	}
}
