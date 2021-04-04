package com.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Respuesta {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_;
	private Usuario autor_;
	private Object contenido_;
	private boolean esDibujo_;
	
	public Respuesta (Usuario autor, Object contenido, boolean tipo){
		this.autor_ = autor;
		this.contenido_ = contenido;
		this.esDibujo_ = tipo;
	}
	
	Object getContenido() {
		return this.contenido_;
	}
	
	boolean esDibujo() {
		return this.esDibujo_;
	}
	
	Usuario getAutor() {
		return this.autor_;
	}
}
