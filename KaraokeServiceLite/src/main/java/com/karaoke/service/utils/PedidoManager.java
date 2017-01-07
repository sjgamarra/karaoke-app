package com.karaoke.service.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.karaoke.service.repository.ParametroRepository;

@Component
@Scope(value = "singleton")
public class PedidoManager {
		
		// Instance for PedidoManager
		private static PedidoManager INSTANCE = null;
	 
		private String []dispositivos;
		private int counter = 0;
		@Autowired
	    private ParametroRepository parametroRepository;
		
		/**
		 * Get Instance for PedidoManager
		 * 
		 * @return
		 */
		public static PedidoManager getInstance() {
			if (INSTANCE == null) {
				INSTANCE = new PedidoManager();
			}
			return INSTANCE;
		}

		/**
		 * 
		 */
		private PedidoManager() {
			super();
			
		}
		
		@PostConstruct
		public void loadDispositivos(){
			try{
				String dispositivosStr = parametroRepository.findByNombre("DISPOSITIVOS").get(0).getValor();
				dispositivos = dispositivosStr.split(",");
			}catch(Exception e){
				dispositivos = new String[0];
			}
		}
		
		public int getCurrentCounter(){
			return counter;
		}
		
		public String[] getDispositivosArray(){
			return dispositivos;
		}
		
		public String getNextDispositivo(){
			if(counter == (dispositivos.length))
				counter = 0;
			
			return dispositivos[counter++];
		}
		
		public int getNumDispositivos(){
			return dispositivos.length;
		}
}
