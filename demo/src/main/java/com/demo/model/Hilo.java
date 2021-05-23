package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Hilo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_;
	
	@OneToMany(mappedBy = "hilo_", fetch = FetchType.EAGER,cascade = CascadeType.ALL) //Estaba en EAGER
	private List<Respuesta> respuestas_;
	
	

	@ManyToOne(fetch = FetchType.EAGER, optional = false) //Este y el de abajo no rompen
	private Usuario jugadorInicial_;
	//Notificar cuando acaba el hilo??
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade = CascadeType.ALL) //Estaban en LAZY y sin cascade
	private Partida partida_;
	
	public Hilo (Usuario inicial,Partida p){
		this.partida_=p; 
		this.jugadorInicial_ = inicial;
		this.respuestas_ = new ArrayList<Respuesta>(); 
	}
	public Hilo() {
		
	}
	
	public Partida getPartida_() {
		return partida_;
	}

	public void setPartida_(Partida partida_) {
		this.partida_ = partida_;
	}
	
	public void addRespuesta(Respuesta respuesta) {
		this.respuestas_.add(respuesta);
	}
	
	
	public Integer getId_() {
		return id_;
	}

	public void setId_(Integer id_) {
		this.id_ = id_;
	}

	public List<Respuesta> getRespuestas_() {
		return respuestas_;
	}

	public void setRespuestas_(List<Respuesta> respuestas_) {
		this.respuestas_ = respuestas_;
	}

	public Usuario getJugadorInicial_() {
		return jugadorInicial_;
	}

	public void setJugadorInicial_(Usuario jugadorInicial_) {
		this.jugadorInicial_ = jugadorInicial_;
	}

	
	public int getSize() {
		return this.respuestas_.size();
	}
}
