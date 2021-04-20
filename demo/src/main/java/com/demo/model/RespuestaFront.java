package com.demo.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;


@Component
public class RespuestaFront {
	
	private int id;
	private String contenido;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public boolean isEsDibujo() {
		return esDibujo;
	}
	public void setEsDibujo(boolean esDibujo) {
		this.esDibujo = esDibujo;
	}
	private boolean esDibujo;
	
	public RespuestaFront (int idRespuesta,boolean esDibujo,String contenido){
		this.id = idRespuesta;
		this.contenido = contenido;
		this.esDibujo = esDibujo;
	}
	public RespuestaFront(){
	}
}
