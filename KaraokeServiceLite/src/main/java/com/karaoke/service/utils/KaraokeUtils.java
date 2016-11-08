package com.karaoke.service.utils;

public class KaraokeUtils {
	public static String checkAtributo(String atributo){
		if(atributo == null || atributo.trim().length() <= 0){
			return "Desconocido";
		}else{
			return atributo;
		}
	}
	
	public static boolean isEmpty(String cadena)
	{
	       boolean isEmpty = false;

	       if (cadena == null || cadena.trim().length() <= 0)
	       {
	    	   isEmpty = true;
	       }

	       return isEmpty;
	}
}
