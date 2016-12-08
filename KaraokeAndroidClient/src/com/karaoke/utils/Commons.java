package com.karaoke.utils;

public final class Commons {
	public static final String CONTEXT_URL = "http://192.168.1.5:8080";
	
	public static final String DEVICE_ID = "MESA_1";
	public static final String TAG = "KAPP";	

	//CANCIONES
	public static final String URL_SONG_GET = CONTEXT_URL + "/canciones/%s/%s";
	
	//PEDIDOS
	public static final String URL_REQUEST_GET = CONTEXT_URL + "/pedidos/%s";
	public static final String URL_REQUEST_POST = CONTEXT_URL + "/pedidos/%s/%s";
	public static final String URL_REQUEST_PUT = CONTEXT_URL + "/pedidos/%s/%s";
}
