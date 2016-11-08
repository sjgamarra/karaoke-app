package com.karaoke.desktop.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static java.nio.file.StandardCopyOption.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.karaoke.desktop.client.utils.KaraokeUtils;
import com.karaoke.desktop.entity.Cancion;
import com.karaoke.desktop.entity.Parametro;

public class GestorBibliotecaCanciones {
	private final static String CONTEXT_SERVER = "http://localhost:8080/";
	private Path repoCancionesPath;
	
	public GestorBibliotecaCanciones(){
		//Cosumimos servicio rest para obtener el parametro Directorio Canciones
		final String uri = CONTEXT_SERVER+"parametro/DIRECTORIO_CANCIONES";
	    RestTemplate restTemplate = new RestTemplate();
	    Parametro parametro = restTemplate.getForObject(uri, Parametro.class);
	    
	    if(parametro != null){
	    	repoCancionesPath = Paths.get(parametro.getValor());
	    	if(!Files.exists(repoCancionesPath)){
	    		//TODO: no se pudo encontrar el directorio de canciones
	    	}
	    	if(!Files.isReadable(repoCancionesPath) && !Files.isWritable(repoCancionesPath))
	    	{
	    		//TODO: permisos insuficientes en el directorio de canciones
	    	}
	    }else{
	    	//TODO: no se encontro el parametro de directotio
	    }
	}
	
	//Al copiar se sobreescribiran los archivos con el mismo nombre
	private void copiarMusica(Path origen,Path destino){
		if(Files.exists(origen) ){
			if(Files.isReadable(origen))
			{
				try {
					//Files.copy(origen, destino, REPLACE_EXISTING, COPY_ATTRIBUTES);
					//Files.copy(origen, destino);
					Files.copy(origen, destino.resolve(origen.getFileName()),REPLACE_EXISTING, COPY_ATTRIBUTES);
					
				} catch (IOException e) {
					//TODO: hacer algo para mostrar esta exception que no se pudo copiar
					e.printStackTrace();
				}
			}else{
				//TODO: no se tienen suficientes para copiar los archivos de origen
				System.out.println("Insuficientes Permisos para copiar archivo de origen");
			}
		}else{
			//TODO: directorio origen no existe
		}
	}
	

	//insertar metadata solo de mp3
	private void insertarMetadata(Path pathArchivo){
		try {
			if(pathArchivo.toString().toUpperCase().endsWith(".MP3"))
			{
				InputStream input = Files.newInputStream(pathArchivo);
				ContentHandler handler = new DefaultHandler();
			    Metadata metadata = new Metadata();
			    Parser parser = new Mp3Parser();
			    ParseContext parseCtx = new ParseContext();
			    parser.parse(input, handler, metadata, parseCtx);
			    input.close();
	
			    // List all metadata
			    
			    Cancion cancion = new Cancion();
			    cancion.setNombre(KaraokeUtils.checkAtributo(pathArchivo.getFileName().toString()));
			    cancion.setCompositor(KaraokeUtils.checkAtributo(metadata.get("xmpDM:composer")));
			    cancion.setGenero(KaraokeUtils.checkAtributo(metadata.get("xmpDM:genre")));
			    cancion.setArtista(KaraokeUtils.checkAtributo(metadata.get("xmpDM:artist")));
			    
			    //Llamar al servicio rest para almacenar la cancion
			    final String uri = CONTEXT_SERVER+"cancion";
			    RestTemplate restTemplate = new RestTemplate();
			    ResponseEntity<Cancion> reponse = restTemplate.postForEntity(uri, cancion, Cancion.class);
			    System.out.println("Status Code Insercion" + reponse.getStatusCode());
			}
		} catch (IOException e) {
			// TODO Excepcion al abrir archivo para obtener metadata
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Excepcion al abrir archivo para obtener metadata
			e.printStackTrace();
        } catch (TikaException e) {
        	// TODO Excepcion al abrir archivo para obtener metadata
        	e.printStackTrace();
        }
	}
	
	public void cargarMusicaEnBatch(String directorio){
		if(!directorio.isEmpty()){
			if(repoCancionesPath!=null){
				Path origenPath = Paths.get(directorio);
				//para recorrer subdirectorios en busca de mas mp3 usar walk
				//try(Stream<Path> files = Files.walk(origenPath)){
				try(Stream<Path> files = Files.list(origenPath)){
					//recorremos y copiamos todos los mp3 del directorio origen
					//se considera que tanto los mp3 y los cdg se encuentran en la misma ubicacion
			        files
			        	.filter(path -> (path.toString().toUpperCase().endsWith(".MP3")||path.toString().toUpperCase().endsWith(".CDG")))
			        	.peek(path -> insertarMetadata(path))
			        	.forEach(path -> copiarMusica(path,repoCancionesPath));
			        	//.forEach(path -> System.out.println(path));  
			    }catch (IOException e){
			    	//TODO: hacer algo para mostrar esta exception de Entrada salida de archivos
			    	e.printStackTrace();
			    }
				
			}else{
				//TODO: hacer algo o mostrar mensaje que no se pudo establecer el path destino
			}
						
		}else{
			//TODO: hacer algo o mostrar mensaje que no se ingreso directorio o directorio vacio
		}
	}
}
