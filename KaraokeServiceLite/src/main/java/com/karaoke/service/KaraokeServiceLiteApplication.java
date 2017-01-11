package com.karaoke.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.karaoke.service.repository.ParametroRepository;
import com.karaoke.service.repository.PedidoRepository;

@SpringBootApplication
public class KaraokeServiceLiteApplication extends WebMvcConfigurerAdapter{

	@Autowired
    private ParametroRepository parametroRepository;
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(KaraokeServiceLiteApplication.class, args);
	}
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		String directorioCanciones = parametroRepository.findByNombre("DIRECTORIO_CANCIONES").get(0).getValor();
	    registry.addResourceHandler("/biblioteca/**")
	    	.addResourceLocations("file:///" + directorioCanciones);
	    
	    pedidoRepository.clearPedidos();
	}
}
