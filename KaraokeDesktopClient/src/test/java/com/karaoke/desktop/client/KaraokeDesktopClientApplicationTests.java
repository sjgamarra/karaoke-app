package com.karaoke.desktop.client;

//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class KaraokeDesktopClientApplicationTests {

	
	public static void main(String[] args) {
		GestorBibliotecaCanciones gestorCanciones = new GestorBibliotecaCanciones();
		gestorCanciones.cargarMusicaEnBatch("E:\\Proyectos P\\Music Samples");
	}
	
	//@Test
	public void contextLoads() {
	}

}
