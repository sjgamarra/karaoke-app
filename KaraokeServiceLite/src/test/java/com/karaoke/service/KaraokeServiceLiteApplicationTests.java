package com.karaoke.service;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.Gson;
import com.karaoke.service.controller.PedidoController;
import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;
import com.karaoke.service.repository.CancionRepository;
import com.karaoke.service.repository.ParametroRepository;
import com.karaoke.service.repository.PedidoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KaraokeServiceLiteApplicationTests {

	@Autowired
    private CancionRepository cancionRepository;
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	@Autowired
    private ParametroRepository parametroRepository;
	
	@Autowired
    private PedidoController pedidoController;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testRepository() {
		/*
		Cancion cancion = new Cancion();
		cancion.setNombre("TestSpring5");
		//cancion.setAutor("TestSpring5");
		cancion.setGenero("TestSpring5");
		cancion.setEstado(1);
		
		//Test Repository
		cancionRepository.save(cancion);
		*/
		
		
		
		//Cancion cancion = cancionRepository.findOne(1L);
		
//		Cancion cancion = cancionRepository.findOne(37L);
//		
//		Pedido pedido = new Pedido();
//		pedido.setDispositivoId("MESA1");
//		pedido.setCancion(cancion);
//		pedido.setEstado(1);
//		pedido.setFechaHora(new Date());
//		
//		pedidoRepository.save(pedido);
		
		
		
//		Cancion cancion2 = cancionRepository.findOne(38L);
//		
//		Pedido pedido2 = new Pedido();
//		pedido2.setDispositivoId("MESA1");
//		pedido2.setCancion(cancion2);
//		pedido2.setEstado(1);
//		pedido2.setFechaHora(new Date());
//		
//		pedidoRepository.save(pedido2);
//		
//		Cancion cancion3 = cancionRepository.findOne(39L);
//		
//		Pedido pedido3 = new Pedido();
//		pedido3.setDispositivoId("MESA2");
//		pedido3.setCancion(cancion3);
//		pedido3.setEstado(1);
//		pedido3.setFechaHora(new Date());
//		
//		pedidoRepository.save(pedido3);
//		
//		Cancion cancion4 = cancionRepository.findOne(40L);
//		
//		Pedido pedido4 = new Pedido();
//		pedido4.setDispositivoId("MESA3");
//		pedido4.setCancion(cancion4);
//		pedido4.setEstado(1);
//		pedido4.setFechaHora(new Date());
//		
//		pedidoRepository.save(pedido4);
		
		
	}
	
	
	 @Test
	 public void realizarPedido() throws Exception {
		 
		String rutaArchivo= "";
			
		String directorioCanciones = parametroRepository.
					findByNombre("DIRECTORIO_CANCIONES").get(0).getValor();
		
		Pedido pedido = pedidoController.obtenerPedido("MESA2");
		rutaArchivo = directorioCanciones + pedido.getCancion().getNombreArchivo();
		System.out.println("Ruta: " + rutaArchivo);
		
		pedido.setEstado(2);
		
		pedidoController.actualizarPedido(pedido);
		
	 }
	
	 @Test
	 public void batchCreation() throws Exception {
			//mockMvc.perform(post("/cargarMusicaBatch/E:\\Proyectos P\\Canciones")).andExpect(status().isOk());
	}
	
	 @Test
	 public void testCancionAdd() throws Exception {
//		 MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//		    params.set("nombre", "MockMVC");
//		    params.set("autor", "LuisT");	 
//		    params.set("genero", "salsa");
		 
		//mockMvc.perform(post("/cancion").params(params)).andExpect(status().isOk());
		 

		 	Cancion cancion1 = new Cancion();
			cancion1.setNombreArchivo("CancionTest1");
			//cancion1.setAutor("CancionTest1");
			cancion1.setGenero("CancionTest1");
			cancion1.setEstado(1);
			
			Gson gson = new Gson();
			String json = gson.toJson(cancion1);
			
		  // mockMvc.perform(post("/cancion").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	}
	 
	 @Test
	 public void testControlleAddListaCanciones() throws Exception {
		 
		 List<Cancion> cancionLst = new ArrayList<>(); 
		 
		 	Cancion cancion1 = new Cancion();
			cancion1.setNombreArchivo("Cancion1");
			//cancion1.setAutor("Cancion1");
			cancion1.setGenero("Cancion1");
			cancion1.setEstado(1);
		 
			Cancion cancion2 = new Cancion();
			cancion2.setNombreArchivo("Cancion2");
			//cancion2.setAutor("Cancion2");
			cancion2.setGenero("Cancion2");
			cancion2.setEstado(1);	
			
			Cancion cancion3 = new Cancion();
			cancion3.setNombreArchivo("Cancion3");
			//cancion3.setAutor("Cancion3");
			cancion3.setGenero("Cancion3");
			cancion3.setEstado(1);	
		
			cancionLst.add(cancion1);
			cancionLst.add(cancion2);
			cancionLst.add(cancion3);
			
			
			Gson gson = new Gson();
			String json = gson.toJson(cancionLst);
			
		    //mockMvc.perform(post("/canciones").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	}

}
