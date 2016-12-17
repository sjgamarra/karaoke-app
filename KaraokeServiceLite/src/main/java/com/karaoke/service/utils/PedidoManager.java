package com.karaoke.service.utils;

public class PedidoManager {
		// Instance for PedidoManager
		private static PedidoManager INSTANCE = null;
	 
		private String []dispositivos = {"MESA 1","MESA 2","MESA 3"};
		private int counter = 0;
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
		
		public String getNextDispositivo(){
			if(counter == (dispositivos.length))
				counter = 0;
			
			return dispositivos[counter++];
		}
		
		public int getNumDispositivos(){
			return dispositivos.length;
		}
}
