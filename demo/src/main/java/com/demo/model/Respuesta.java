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


	public byte[] getContenido_() {
		return contenido_;
	}

	public void setContenido_(byte[] contenido_) {
		this.contenido_ = contenido_;
		if(this.esDibujo == false) {
			this.frase = new String(contenido_,StandardCharsets.UTF_8);
		}else {
			this.frase=null;
		}
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
	@Column(name="contenido")
	private byte[] contenido_;
	
	private boolean esDibujo;
	
	private String frase;
	
	public boolean isEsDibujo() {
		return esDibujo;
	}

	public void setEsDibujo(boolean esDibujo) {
		this.esDibujo = esDibujo;
	}

	public Respuesta (Usuario autor, byte[] contenido,boolean dibujo){
		this.autor_ = autor;
		this.contenido_ = contenido;
		this.esDibujo = dibujo;
		if(dibujo == false) {
			this.frase = new String(contenido,StandardCharsets.UTF_8);
		}else {
			this.frase=null;
		}
	}
	public Respuesta (){
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}


	
}
