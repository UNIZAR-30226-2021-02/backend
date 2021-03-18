package com.demo.model;

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
	
	
	
	
	
	 @ManyToMany(mappedBy="amigo")
	 private List<Usuario> usuario;
	 
	 @ManyToMany(mappedBy="peticion")
	 private List<Usuario> usuario2;
	
	
	public List<Usuario> getAmigo() {
		return amigo;
	}

	public void setAmigo(Usuario amigo) {
		this.amigo.add(amigo);
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
}
