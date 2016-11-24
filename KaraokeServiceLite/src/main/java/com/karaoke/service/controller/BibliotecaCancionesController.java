package com.karaoke.service.controller;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.karaoke.service.entity.Cancion;
import com.karaoke.service.entity.Parametro;
import com.karaoke.service.repository.CancionRepository;
import com.karaoke.service.repository.ParametroRepository;
import com.karaoke.service.utils.KaraokeUtils;

@RestController
public class BibliotecaCancionesController {
	@Autowired
    private CancionRepository cancionRepository;
	
	@Autowired
    private ParametroRepository parametroRepository;
	
	
	//Al copiar se sobreescribiran los archivos con el mismo nombre
		private void copiarMusica(Path origen,Path destino){
			if(Files.exists(origen) ){
				if(Files.isReadable(origen))
				{
					try {
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
			    
				    Cancion cancion = new Cancion();
				    cancion.setNombre(KaraokeUtils.checkAtributo(pathArchivo.getFileName().toString()));
				    cancion.setAlbum(KaraokeUtils.checkAtributo(metadata.get("xmpDM:album")));
				    cancion.setGenero(KaraokeUtils.checkAtributo(metadata.get("xmpDM:genre")));
				    cancion.setArtista(KaraokeUtils.checkAtributo(metadata.get("xmpDM:artist")));
				    
				    //grabar cancion en repostorio
				    cancionRepository.save(cancion);
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
		
		@RequestMapping(value = "/cargarMusicaBatch/{directorio}", method = RequestMethod.POST)
		public void cargarMusicaEnBatch(@PathVariable("directorio") String directorio){
			if(!KaraokeUtils.isEmpty(directorio)){
				Parametro dirDestinoParam = parametroRepository.findByNombre("DIRECTORIO_CANCIONES").get(0);
				Path repoCancionesPath = Paths.get(dirDestinoParam.getValor());
				
				if(!Files.exists(repoCancionesPath)){
		    		//TODO: no se pudo encontrar el directorio de canciones
		    	}
		    	if(!Files.isReadable(repoCancionesPath) && !Files.isWritable(repoCancionesPath))
		    	{
		    		//TODO: permisos insuficientes en el directorio de canciones
		    	}
				
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
