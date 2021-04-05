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
	

	@OneToOne

	private Usuario autor_;

	

	
	@Lob
	@Column(name="contenido",columnDefinition="bytea")
	private Object contenido_;
	

	private boolean esDibujo_;
	
	public Respuesta (Usuario autor, byte[] contenido, boolean tipo){
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
