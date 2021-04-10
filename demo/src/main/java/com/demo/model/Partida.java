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
	private int id_;
	private String nombre_;
	private int nJugadores_;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
	        name = "jugadores",
	        joinColumns = @JoinColumn(name = "partida", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mailJugador", nullable = false)
	    )

	private List<Usuario> jugadores_;
	
	
	
	
	@ManyToMany(mappedBy="invitaciones")
	private List <Usuario> invitados_;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)

	private Usuario host_;
	
	private String estado_;  //esperando/puntuando/jugando
	
	
	
	@OneToMany(mappedBy = "partida_", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Hilo> hilos_; //mismo tamaño que jugadores
	
	
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
		
	public Partida (Usuario host,String nombrePartida) {
		this.nombre_ = nombrePartida; 
		this.host_ = host;
		this.estado_ = "esperando";
		this.nJugadores_ = 1;
		this.hilos_ = new ArrayList<Hilo>();
		this.jugadores_ = new ArrayList<Usuario>();
		this.jugadores_.add(host);
	}
	
	public Partida () {
		
	}
	public void setNull() {
		this.host_.setPassword(null);
		this.host_.setNull();
		this.hilos_ = null;
		this.jugadores_ = null;
	}
	public void addHilo(Hilo hilo) {
		this.hilos_.add(hilo);
	}
	
	public void addJugador(Usuario jugador) {
		this.jugadores_.add(jugador);
		nJugadores_++;
	}
	
	public int getId() {
		return this.id_;
	}
	
	
	
	public boolean isUser(String usuario) {
		for (Usuario u : jugadores_) {
			if(u.getNombre().equals(usuario)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInvited(String usuario) {
		for (Usuario u : invitados_) {
			if(u.getNombre().equals(usuario)) {
				return true;
			}
		}
		return false;
	}
	
	public int getnJugadores_() {
		return nJugadores_;
	}
	public void setnJugadores_(int nJugadores_) {
		this.nJugadores_ = nJugadores_;
	}
	public List<Hilo> getHilos_() {
		return hilos_;
	}
	public String getNombre() {
		return nombre_;
	}
	public void setNombre(String nombre) {
		this.nombre_ = nombre;
	}

}
