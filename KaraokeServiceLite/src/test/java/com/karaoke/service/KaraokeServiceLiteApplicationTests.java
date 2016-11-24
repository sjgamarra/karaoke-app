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
import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Pedido;
import com.karaoke.service.repository.CancionRepository;
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
		
		Cancion cancion = new Cancion();
		cancion.setId(2L);		
		Pedido pedido = new Pedido();
		pedido.setDispositivoId("DEMO");
		pedido.setCancion(cancion);
		pedido.setEstado(1);
		pedido.setFechaHora(new Date());
		
		pedidoRepository.save(pedido);
		
	}
	
	 @Test
	 public void batchCreation() throws Exception {
			//mockMvc.perform(post("/cargarMusicaBatch/E:\\Proyectos P\\Music Samples")).andExpect(status().isOk());
	}
	
	 @Test
	 public void testCancionAdd() throws Exception {
//		 MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//		    params.set("nombre", "MockMVC");
//		    params.set("autor", "LuisT");	 
//		    params.set("genero", "salsa");
		 
		//mockMvc.perform(post("/cancion").params(params)).andExpect(status().isOk());
		 

		 	Cancion cancion1 = new Cancion();
			cancion1.setNombre("CancionTest1");
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
			cancion1.setNombre("Cancion1");
			//cancion1.setAutor("Cancion1");
			cancion1.setGenero("Cancion1");
			cancion1.setEstado(1);
		 
			Cancion cancion2 = new Cancion();
			cancion2.setNombre("Cancion2");
			//cancion2.setAutor("Cancion2");
			cancion2.setGenero("Cancion2");
			cancion2.setEstado(1);	
			
			Cancion cancion3 = new Cancion();
			cancion3.setNombre("Cancion3");
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
