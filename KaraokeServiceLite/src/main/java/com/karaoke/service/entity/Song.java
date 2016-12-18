package com.karaoke.service.entity;

public class Song {
	private Long id;
	private String title;
	private String artist;
	private String genre;
	private Integer status;
	private Long request;
	private String device;
	
	public Song(Long id, String title, String artist, String genre, Integer status, Long request, String device) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.status = status;
		this.request = request;
		this.device = device;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getRequest() {
		return request;
	}
	public void setRequest(Long request) {
		this.request = request;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}	
}
