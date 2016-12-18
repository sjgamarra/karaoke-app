package com.karaoke.utils;

public final class Commons {
	//IP CONTEXTO
	public static final String CONTEXT_URL = "http://192.168.0.100:8080";
	
	//CONFIGS
	//public static final String DEVICE_ID = "MESA_1";
	public static final String APP_TAG = "KAPP";
	public static final long APP_RECALL = 5000;

	//URLS GENEROS
	public static final String URL_GENRE_GET = CONTEXT_URL + "/generos";
	
	//URLS CANCIONES
	public static final String URL_SONG_GET = CONTEXT_URL + "/canciones/%s/%s";
	
	//URLS PEDIDOS
	public static final String URL_REQUEST_GET = CONTEXT_URL + "/pedidos/%s";
	public static final String URL_REQUEST_POST = CONTEXT_URL + "/pedidos/%s/%s";
	public static final String URL_REQUEST_PUT = CONTEXT_URL + "/pedidos/%s/%s";
	
	//MENSAJES
	public static final String MSG_ALERT_NOT_FOUND = "No se encontraron resultados.";
	public static final String MSG_ALERT_ADD_SONG = "\u00bfDesea a\u00f1adir esta canci\u00F3n a su lista?";
	public static final String MSG_ALERT_DEL_SONG = "\u00bfDesea eliminar esta canci\u00F3n de su lista?";
	public static final String MSG_ALERT_YES = "S\u00ed";
	public static final String MSG_ALERT_NO = "No";
	
	public static final String MSG_TOAST_REQUEST_PLAYING = "Su pedido se est\u00E1 reproduciendo.";
	public static final String MSG_TOAST_SONG_ADDED_SUCCESS = "La canci\u00F3n ha sido agregada.";
	public static final String MSG_TOAST_SONG_ADDED_FAIL = "La canci\u00F3n no puede ser agregada.";
	public static final String MSG_TOAST_SONG_CANCELED_SUCCESS = "La canci\u00F3n ha sido cancelada.";
	public static final String MSG_TOAST_SONG_CANCELED_FAIL = "La canci\u00F3n no puede ser cancelada.";
	
}
