package com.karaoke.service.client.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.karaoke.service.client.Cancion;
import com.karaoke.service.client.CancionService;
import com.karaoke.service.client.CancionServiceImplService;

public class CancionServiceClientTest {
	private static final QName SERVICE_NAME = new QName("http://localhost:8080/", "CancionServiceImplService");
	
	public static void main(String args[]) throws java.lang.Exception {
		URL wsdlURL = CancionServiceImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        //Probando llamada el Servicio
        CancionServiceImplService ss = new CancionServiceImplService(wsdlURL, SERVICE_NAME);
        CancionService portCancion = ss.getCancionServiceImplPort();  
        
        Cancion cancion = new Cancion();
		cancion.setNombre("TestCliente");
		cancion.setAutor("TestCliente");
		cancion.setGenero("TestCliente");
		cancion.setEstado(1);
		
		portCancion.crearCancion(cancion);
	}
}
