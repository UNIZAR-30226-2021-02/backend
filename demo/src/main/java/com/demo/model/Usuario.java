package com.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private Integer estrellas;
	private Integer monedas;
	private Integer pDibujo;
	private Integer pListo;
	private Integer pGracioso;
	private Integer nAmigos;
	
	public Integer getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(Integer estrellas) {
		this.estrellas = estrellas;
	}

	
	public int compareTo(Usuario u) {
	    if (getEstrellas() == null || u.getEstrellas() == null) {
	      return 0;
	    }
	    return getEstrellas().compareTo(u.getEstrellas());
	  }
	
	
	public Integer getMonedas() {
		return monedas;
	}

	public void setMonedas(Integer monedas) {
		this.monedas = monedas;
	}

	public Integer getpDibujo() {
		return pDibujo;
	}

	public void setpDibujo(Integer pDibujo) {
		this.pDibujo = pDibujo;
	}

	public Integer getpListo() {
		return pListo;
	}

	public void setpListo(Integer pListo) {
		this.pListo = pListo;
	}

	public Integer getpGracioso() {
		return pGracioso;
	}

	public void setpGracioso(Integer pGracioso) {
		this.pGracioso = pGracioso;
	}

	public Integer getnAmigos() {
		return nAmigos;
	}

	public void setnAmigos(Integer nAmigos) {
		this.nAmigos = nAmigos;
	}

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
	        name = "amigos",
	        joinColumns = @JoinColumn(name = "mailUsuario", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mailAmigo", nullable = false)
	    )
	private List<Usuario> amigo;
	
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
	        name = "peticiones",
	        joinColumns = @JoinColumn(name = "mail_recibe", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mail_envia", nullable = false)
	    )
	private List<Usuario> peticion;
	
	
	
	 public List<Usuario> getPeticion() {
		 List<Usuario> peticiones = new ArrayList<>();
			if(this.peticion != null) {
				for(Usuario a : peticion) {
					a.printUser();
					Usuario u = new Usuario();
					u.setMail(a.getMail());
					u.setNombre(a.getNombre());
					u.setPuntos(a.getpGracioso(),a.getpListo(),a.getpDibujo(),a.getEstrellas(),a.getMonedas());
					u.setFotPerf(a.getFotPerf());
					peticiones.add(u);
				}
				return peticiones;
			}
			return null;
	}
	
	 
	
	 
	 
	 @ManyToMany(mappedBy="invitado",fetch = FetchType.EAGER)
	 private List<Invitaciones> invitaciones;
	 
	 
	 
	 public List<Invitaciones> getInvitaciones() {
		return invitaciones;
	}

	public void setInvitaciones(List<Invitaciones> invitaciones) {
		this.invitaciones = invitaciones;
	}

	@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(
		        name = "desbloqueadas",
		        joinColumns = @JoinColumn(name = "foto", nullable = false),
		        inverseJoinColumns = @JoinColumn(name="usuario", nullable = false)
		    )

		private List<Foto> fotos = new ArrayList<>();
	 
	 
	 
	 public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	
	public void addFoto(Foto f) {
		fotos.add(f);
	}
	public boolean noComprada(Foto f) {
		return !fotos.contains(f);
	}

	@ManyToMany(mappedBy="invitador")
	 private List<Invitaciones> invitador;
	 
	 
	 
	
	/*
	public void deleteInvite(Invitaciones i) {
		if(this.invitaciones != null) {
			for(Invitaciones a : invitaciones) {
				if(a.getInvitado().getNombre() == nombre) {
					System.out.println(invitaciones.remove(a));
				}
			}
		}
	}*/
	
	public boolean contiene(Usuario usuario) {
		String nombre = usuario.getNombre();
		if(this.peticion != null) {
			for(Usuario a : peticion) {
				if(a.getNombre() == nombre) {
					return true;
				}
			}
		}
		return false;	
	}
	 
	public void setNull() {
		this.amigo=null;
		this.password=null;
		this.peticion=null;
		this.partidas=null;
		this.partidasHost=null;
		this.respuestas=null;
		this.invitaciones=null;
		
			
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

	 @ManyToMany(mappedBy="jugadores_",fetch = FetchType.EAGER)	
	 private List<Partida> partidas;
	 
	 @ManyToMany(mappedBy="peticion",fetch = FetchType.EAGER)
	 private List<Usuario> usuario2;
	
	
	 @ManyToMany(mappedBy="amigo",fetch = FetchType.EAGER)	
	 private List<Usuario> usuario;
	 

	 @OneToMany(mappedBy = "host_",fetch = FetchType.EAGER,cascade = CascadeType.ALL) //NO TOCAR

	 private List<Partida> partidasHost;
	 
	 public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}




	public List<Partida> getPartidasHost() {
		return partidasHost;
	}

	public void setPartidasHost(List<Partida> partidasHost) {
		this.partidasHost = partidasHost;
	}

	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	

	@OneToMany(mappedBy = "autor_",fetch = FetchType.EAGER) 
	 
	 private List<Respuesta> respuestas;
	 
	public List<Usuario> getAmigo() {
		
		List<Usuario> amigos = new ArrayList<>();
		if(this.amigo != null) {
			for(Usuario a : amigo) {
				Usuario u = new Usuario();
				u.setMail(a.getMail());
				u.setNombre(a.getNombre());
				u.setPuntos(a.getpGracioso(),a.getpListo(),a.getpDibujo(),a.getEstrellas(),a.getMonedas());
				
				u.setFotPerf(a.getFotPerf());
				amigos.add(u);
			}
			
			//ordenarAmigos(amigos);
			amigos.sort(Comparator.comparing(Usuario::getEstrellas).reversed());
			
			return amigos;
		}
		return null;
	}
	
	public void setPuntos(Integer pGracioso, Integer pListo, Integer pDibujar, Integer estrellas, Integer monedas) {
		this.pGracioso = pGracioso;
		this.pListo = pListo;
		this.pDibujo = pDibujar;
		this.estrellas = estrellas;
		this.monedas = monedas;
	}
	
	public void votarGracioso(int cantidad) {
		this.pGracioso = pGracioso+cantidad;
	}
	public void votarListo(int cantidad) {
		this.pListo = pGracioso+cantidad;
	}
	public void votarDibujo(int cantidad) {
		this.pDibujo = pGracioso+cantidad;
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
		System.out.println(this.nombre+"--"+this.password+"--"+"--"+this.token+"--"+this.mail+"--");
	}

	public String getFotPerf() {
		return fotPerf;
	}

	public void setFotPerf(String fotPerf) {
		this.fotPerf = fotPerf;
	}
	
	public boolean esAmigoDe(Usuario u) {
		for(Usuario a : amigo) {
			if (a.getNombre().equalsIgnoreCase(u.getNombre())){
				return true;
			}
		}
		return false;
	}

	public Usuario() {
		
	}
	
	public Usuario(Usuario u) {
		this.fotPerf = u.fotPerf;
		this.mail = u.mail;
		this.monedas = u.monedas;
		this.nAmigos = u.nAmigos;
		this.nombre = u.nombre;
		this.password = u.password;
		this.pDibujo = u.pDibujo;
		this.pGracioso = u.pGracioso;
		this.pListo = u.pListo;
		this.role = u.role;
		this.estrellas = u.estrellas;
		this.fotos = u.fotos;
	}
}
