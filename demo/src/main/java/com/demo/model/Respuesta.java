package com.demo.model;


import java.nio.charset.StandardCharsets;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.FetchType;

import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;




@Entity
public class Respuesta {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_;
	

	public Integer getId_() {
		return id_;
	}

	public Usuario getAutor_() {
		return autor_;
	}

	public void setAutor_(Usuario autor_) {
		this.autor_ = autor_;
	}


	public byte[] getDibujo() {
		return dibujo;
	}

	public void setDibujo(byte[] contenido_) {
		this.dibujo = contenido_;
	}

	public void setHilo(Hilo h) {
		this.hilo_= h;
	}
	@OneToOne

	private Usuario autor_;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Hilo hilo_;

	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name="dibujo")
	private byte[] dibujo;
	
	private boolean esDibujo;
	
	private String frase;
	
	public boolean isEsDibujo() {
		return esDibujo;
	}

	public void setEsDibujo(boolean esDibujo) {
		this.esDibujo = esDibujo;
	}

	public Respuesta (Usuario autor, byte[] dibujo,boolean esDibujo,String frase){
		this.autor_ = autor;
		this.dibujo = dibujo;
		this.esDibujo = esDibujo;
		this.frase=frase;
	}
	
	public Respuesta (int id) {
		this.id_ = id;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}
	
	public Respuesta() {};


	
}
