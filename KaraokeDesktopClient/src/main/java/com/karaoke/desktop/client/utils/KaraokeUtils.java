package com.karaoke.desktop.client.utils;

public class KaraokeUtils {
	public static String checkAtributo(String atributo){
		if(atributo == null || atributo.trim().length() <= 0){
			return "Desconocido";
		}else{
			return atributo;
		}
	}
}
