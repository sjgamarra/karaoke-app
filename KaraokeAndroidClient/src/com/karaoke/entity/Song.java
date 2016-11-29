package com.karaoke.entity;

public class Song {

	private Long id;
	private String title;
	private String artist;
	private String genre;
	private Integer status;

	public Song(Long id, String title, String artist, String genre, Integer status) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.status = status;
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
		
}
