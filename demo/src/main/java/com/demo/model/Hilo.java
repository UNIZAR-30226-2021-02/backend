package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;


public class Hilo {

	private List<Respuesta> respuestas_;
	private Usuario jugadorInicial_;
	//Notificar cuando acaba el hilo??
	
	
	Hilo (Usuario inicial){
		this.jugadorInicial_ = inicial;
		this.respuestas_ = new ArrayList<Respuesta>(); 
	}
	
	public void addRespuesta(Respuesta respuesta) {
		this.respuestas_.add(respuesta);
	}
	
	public List<Respuesta> getRespuestas() {
		return this.respuestas_;
	}
	
	public Usuario getjugadorInicial() {
		return this.jugadorInicial_;
	}
	
	public int getSize() {
		return this.respuestas_.size();
	}
}
