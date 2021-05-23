package com.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Foto {

	@Id
	private String idFoto;
	private Integer precio;
	
	
	@ManyToMany(mappedBy="fotos",fetch = FetchType.EAGER)
	private List<Usuario> usDesbloqueados;
	
	public Foto(Integer precio,String idFoto) {
		this.precio = precio;
		this.idFoto = idFoto;
	}
	
	public Foto() {};
	
	public String getIdFoto() {
		return idFoto;
	}
	

	public void setIdFoto(String idFoto) {
		this.idFoto = idFoto;
	}


	public Integer getPrecio() {
		return precio;
	}


	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	/*
	public List<Usuario> getUsDesbloqueados() {
		return usDesbloqueados;
	}
	*/

	public void setUsDesbloqueados(List<Usuario> usDesbloqueados) {
		this.usDesbloqueados = usDesbloqueados;
	}
	
	
}
