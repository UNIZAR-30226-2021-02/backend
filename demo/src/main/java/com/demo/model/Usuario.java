package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
public class Usuario {

	@Id
	private String mail;
	private String nombre;
	private String password;
	private String token;
	private String role;
	private String fotPerf;
	@Column(columnDefinition = "integer default 0")
	private Integer puntos;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
	        name = "amigos",
	        joinColumns = @JoinColumn(name = "mailUsuario", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mailAmigo", nullable = false)
	    )
	private List<Usuario> amigo;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
	        name = "peticiones",
	        joinColumns = @JoinColumn(name = "mailUsuario", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mailPedido", nullable = false)
	    )
	private List<Usuario> peticion;
	
	
	
	 public List<Usuario> getPeticion() {
		 List<Usuario> peticiones = new ArrayList<>();
			if(this.peticion != null) {
				for(Usuario a : peticion) {
					Usuario u = new Usuario();
					u.setNombre(a.getNombre());
					peticiones.add(u);
				}
				return peticiones;
			}
			return null;
	}
	
	public void setNull() {
			this.amigo=null;
			this.peticion=null;
			
			}

	public boolean setPeticion(Usuario peticion) {
		if(!this.peticion.contains(peticion) && !this.amigo.contains(peticion)) {
			this.peticion.add(peticion);
			return true;
		}
		return false;
	}
	
	public void deletePeticion(Usuario peticion) {
		this.peticion.remove(peticion);
	}

	@ManyToMany(mappedBy="amigo")	
	 private List<Usuario> usuario;
	 
	 @ManyToMany(mappedBy="peticion")
	 private List<Usuario> usuario2;
	
	
	public List<Usuario> getAmigo() {
		
		List<Usuario> amigos = new ArrayList<>();
		if(this.amigo != null) {
			for(Usuario a : amigo) {
				Usuario u = new Usuario();
				u.setNombre(a.getNombre());
				amigos.add(u);
			}
			return amigos;
		}
		return null;
	}

	public void setAmigo(Usuario amigo) {
		this.amigo.add(amigo);
	}
	
	public void deleteAmigo(Usuario amigo) {
		this.amigo.remove(amigo);
	}

	public String getMail() {
		return mail;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public void printUser() {
		System.out.println(this.nombre+this.password+"--"+"--"+this.token+"--"+this.mail+"--");
	}

	public String getFotPerf() {
		return fotPerf;
	}

	public void setFotPerf(String fotPerf) {
		this.fotPerf = fotPerf;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
}
