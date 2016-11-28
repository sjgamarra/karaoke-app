package com.karaoke.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the "Cancion" database table.
 * 
 */
@Entity
@Table(name="\"Cancion\"")
@NamedQuery(name="Cancion.findAll", query="SELECT c FROM Cancion c")
public class Cancion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	//private String autor;

	private Integer estado;

	private String genero;

	@Column(unique=true)
	private String nombre;
	
	private String artista;
	
	private String album;

	//bi-directional many-to-one association to Pedido
//	@OneToMany(mappedBy="cancion")
//	private List<Pedido> pedidos;

	public Cancion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

//	public List<Pedido> getPedidos() {
//		return this.pedidos;
//	}
//
//	public void setPedidos(List<Pedido> pedidos) {
//		this.pedidos = pedidos;
//	}
//
//	public Pedido addPedido(Pedido pedido) {
//		getPedidos().add(pedido);
//		pedido.setCancion(this);
//
//		return pedido;
//	}
//
//	public Pedido removePedido(Pedido pedido) {
//		getPedidos().remove(pedido);
//		pedido.setCancion(null);
//
//		return pedido;
//	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	
}