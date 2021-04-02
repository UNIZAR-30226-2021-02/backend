package com.demo.model;

public class Respuesta {
    
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
