package com.karaoke.service;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.repository.CancionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KaraokeServiceLiteApplicationTests {

	@Autowired
    private CancionRepository cancionRepository;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testRepository() {
		Cancion cancion = new Cancion();
		cancion.setNombre("TestSpring5");
		cancion.setAutor("TestSpring5");
		cancion.setGenero("TestSpring5");
		cancion.setEstado(1);
		
		//Test Repository
		//cancionRepository.save(cancion);
	}
	
	 @Test
	 public void testControlleAdd() throws Exception {
		 MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		    params.set("nombre", "MockMVC");
		    params.set("autor", "LuisT");	 
		    params.set("genero", "salsa");
		 
		mockMvc.perform(post("/cancion").params(params)).andExpect(status().isOk());
	}

}
