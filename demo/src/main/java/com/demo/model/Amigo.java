package com.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Amigo {

	
	
	
	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String mailUsuario;
	private String mailAmigo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMailUsuario() {
		return mailUsuario;
	}
	public void setMailUsuario(String mailUsuario) {
		this.mailUsuario = mailUsuario;
	}
	public String getMailAmigo() {
		return mailAmigo;
	}
	public void setMailAmigo(String mailAmigo) {
		this.mailAmigo = mailAmigo;
	}
	
	
	
	
	
	
	
	
}
