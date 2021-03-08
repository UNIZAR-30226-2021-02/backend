package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Persona {

	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="nombre",length = 50)
	private String nombre;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getId() {
		return id;
	}
	
}
