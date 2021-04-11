package com.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invitaciones {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@ManyToOne
	private Usuario invitador;
	
	@ManyToOne
	private Usuario invitado;
	
	@ManyToOne
	private Partida partida;
	
	
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Usuario getInvitador() {
		return invitador;
	}
	public void setInvitador(Usuario invitador) {
		this.invitador = invitador;
	}
	public Usuario getInvitado() {
		return invitado;
	}
	public void setInvitado(Usuario invitado) {
		this.invitado = invitado;
	}
	public Partida getPartida() {
		return partida;
	}
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	
	public void setInvitadorNull() {
		invitador.setNull();
	}
	public void setPartidaNull() {
		partida.setNull();
	}
	
	
}
