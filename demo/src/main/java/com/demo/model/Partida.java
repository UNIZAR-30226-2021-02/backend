package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Partida {
	
	//Identificador??
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id_;
	private int nJugadores_;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
	        name = "jugadores",
	        joinColumns = @JoinColumn(name = "partida", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mailJugador", nullable = false)
	    )

	private List<Usuario> jugadores_;
	

	@ManyToOne(fetch = FetchType.LAZY, optional = false)

	private Usuario host_;
	
	private String estado_;  //esperando/puntuando/jugando
	
	
	
	//private List<Hilo> hilos_; //mismo tamaño que jugadores
	
	
	public List<Usuario> getJugadores_() {
		return jugadores_;
	}
	public void setJugadores_(List<Usuario> jugadores_) {
		this.jugadores_ = jugadores_;
	}
	public Usuario getHost_() {
		return host_;
	}
	public void setHost_(Usuario host_) {
		this.host_ = host_;
	}
	public String getEstado_() {
		return estado_;
	}
	public void setEstado_(String estado_) {
		this.estado_ = estado_;
	}
	/*
	public void addRespuesta(Usuario inicial, Respuesta respuesta) {
		for (Hilo h : hilos_) {
			if(h.getjugadorInicial().equals(inicial)) {
				h.addRespuesta(respuesta);
				break;
			}
		}
	}
	
	boolean terminada() {
		for (Hilo h : hilos_) {
			if(h.getSize() < this.nJugadores_) {
				return false;
			}
		}
		this.estado_ = "puntuando";
		return true;
	}
	
	Hilo getHiloJugador(Usuario jugador) {
		for (Hilo h : hilos_) {
			if(h.getjugadorInicial().equals(jugador)) {
				return h;
			}
		}
		return null;
	}
		
	public Partida (Usuario host,int id) {
		this.host_ = host;
		this.estado_ = "esperando";
		this.nJugadores_ = 1;
		this.id_ = id;
		this.hilos_ = new ArrayList<Hilo>();
		this.hilos_.add(new Hilo(host));
		this.jugadores_ = new ArrayList<Usuario>();
		this.jugadores_.add(this.host_);
	}
	
	public void addJugador(Usuario jugador) {
		this.jugadores_.add(jugador);
		nJugadores_++;
		this.hilos_.add(new Hilo(jugador));
	}
	*/
	public int getId() {
		return this.id_;
	}
	
	public int getNumJugadores() {
		return this.nJugadores_;
	}
	
	public boolean isUser(Usuario usuario) {
		for (Usuario u : jugadores_) {
			if(u.getNombre().equals(usuario.getNombre())) {
				return true;
			}
		}
		return false;
	}

}
