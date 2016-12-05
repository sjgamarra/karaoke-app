package com.karaoke.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class KaraokeServiceLiteApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(KaraokeServiceLiteApplication.class, args);
	}
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/biblioteca/**")
	            .addResourceLocations("file:///E:/Canciones/");
	}
}
