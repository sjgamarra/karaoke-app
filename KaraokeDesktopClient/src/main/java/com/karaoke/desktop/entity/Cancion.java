package com.karaoke.desktop.entity;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the "Cancion" database table.
 * 
 */

public class Cancion implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	//private String autor;

	private Integer estado;

	private String genero;

	private String nombre;

	private List<Pedido> pedidos;
	
	private String artista;
	
	private String compositor;

	public Cancion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getAutor() {
//		return this.autor;
//	}
//
//	public void setAutor(String autor) {
//		this.autor = autor;
//	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setCancion(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setCancion(null);

		return pedido;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getCompositor() {
		return compositor;
	}

	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}

	
}