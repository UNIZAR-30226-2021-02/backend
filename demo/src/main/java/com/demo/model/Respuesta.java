package com.demo.model;


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




@Entity
public class Respuesta {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_;
	

	public Integer getId_() {
		return id_;
	}

	public void setId_(Integer id_) {
		this.id_ = id_;
	}

	public Usuario getAutor_() {
		return autor_;
	}

	public void setAutor_(Usuario autor_) {
		this.autor_ = autor_;
	}

	public Hilo getHilo_() {
		return hilo_;
	}

	public void setHilo_(Hilo hilo_) {
		this.hilo_ = hilo_;
	}

	public Object getContenido_() {
		return contenido_;
	}

	public void setContenido_(Object contenido_) {
		this.contenido_ = contenido_;
	}

	public boolean isEsDibujo_() {
		return esDibujo_;
	}

	public void setEsDibujo_(boolean esDibujo_) {
		this.esDibujo_ = esDibujo_;
	}

	@OneToOne

	private Usuario autor_;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Hilo hilo_;

	
	@Lob
	@Column(name="contenido",columnDefinition="bytea")
	private Object contenido_;
	

	private boolean esDibujo_;
	
	public Respuesta (Usuario autor, byte[] contenido, boolean tipo, Hilo hilo){
		this.autor_ = autor;
		this.contenido_ = contenido;
		this.esDibujo_ = tipo;
		this.hilo_ = hilo;
	}
	
	
	
	
	
}
