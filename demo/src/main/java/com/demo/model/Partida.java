package com.demo.model;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.naming.Context;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PreRemove;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.demo.DemoApplication;
import com.demo.repository.HiloRepo;
import com.demo.repository.PartidaRepo;
import com.demo.repository.RespuestaRepo;
import com.demo.service.GameService;





@EnableSpringConfigured
@Configurable
@Entity
public class Partida {
	
	
	
	
	
		
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_;
	private String nombre_;
	private int nJugadores_;
	
	
	
	
	

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(
	        name = "jugadores",
	        joinColumns = @JoinColumn(name = "partida", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="mailJugador", nullable = false)
	    )

	private List<Usuario> jugadores_;
	

	
	@OneToMany(mappedBy="partida")
	private List<Invitaciones> invitaciones;
		

	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Usuario host_;
	
	private String estado_;  //esperando/puntuando/jugando
	
	
	
	@OneToMany(mappedBy = "partida_", fetch = FetchType.EAGER,cascade = CascadeType.ALL) //NO TOCAR ESTE CASCADE
	@OrderColumn
	private Hilo[] hilos_; //mismo tamaño que jugadores
	
	private Integer turno_;
	
	
	
	public List<Usuario> getJugadores_() {
		return jugadores_;
	}
	public void setJugadores_(List<Usuario> jugadores_) {
		this.jugadores_ = jugadores_;
	}
	public Usuario getHost_() {
		return host_;
	}
	public void setHost_(Usuario host_) {
		this.host_ = host_;
	}
	public String getEstado_() {
		return estado_;
	}
	public void setEstado_(String estado_) {
		this.estado_ = estado_;
	}
	
	public Hilo addRespuesta(Usuario inicial, Respuesta respuesta) {
		String idUser= inicial.getMail();
		int i = getHiloJugador(idUser);
		if(i==-1) {
			//El jugador no pertenece a la partida (no tiene hilo)
			return null;
		}
		int j = (i+turno_)%nJugadores_;
		System.out.println("------");
		System.out.println("HiloJug:"+i+"  HiloTurno:"+j+"  Turno:"+turno_);
		System.out.println("------");
		if(hilos_[j].getSize()>turno_) {
			System.out.println("Hilo size mayor :"+hilos_[j].getSize());
			return null;
		}else {
			hilos_[j].addRespuesta(respuesta);
			opTurno();
			return hilos_[j];
		}
	}
	
	
	public Hilo getHiloRespuesta(Usuario inicial) {
		String idUser= inicial.getMail();
		int i = getHiloJugador(idUser);
		if(i==-1) {
			//El jugador no pertenece a la partida (no tiene hilo)
			return null;
		}
		int j = (i+turno_)%nJugadores_;
		System.out.println("------");
		System.out.println("HiloJug:"+i+"  HiloTurno:"+j+"  Turno:"+turno_);
		System.out.println("------");
		if(turnoJugado(idUser)) {
			return null;
		}else {
			return hilos_[j];
		}
	}
	
	
	public int getHiloJugador(String jugador) {
		for(int i=0;i<nJugadores_;i++) {
			System.out.println(hilos_[i].getJugadorInicial_().getMail());
			if(hilos_[i].getJugadorInicial_().getMail().equals(jugador)) {
				System.out.println("Hilo inicial:"+i);
				return i;
				
			}
		}
		return -1; //Error, no se encontró el hilo
	}
	
	
	
	public Partida (Usuario host,String nombrePartida) {
		this.nombre_ = nombrePartida; 
		this.host_ = host;
		this.estado_ = "esperando";
		this.nJugadores_ = 1;
		this.jugadores_ = new ArrayList<Usuario>();
		this.jugadores_.add(host);
		this.turno_=-1; //Sin empezar
		
	}
	
	public Partida () {
		
	}
	public void setNull() {
		this.host_.setPassword(null);
		this.host_.setNull();
		this.hilos_ = null;
		this.jugadores_ = null;
		
	}
	public void setNullConInvitadores() {
		this.host_.setPassword(null);
		this.host_.setNull();
		this.hilos_ = null;
		this.jugadores_ = null;
	}
	
	public void addJugador(Usuario jugador) {
		this.jugadores_.add(jugador);
		nJugadores_++;
	}
	
	public int getId() {
		return this.id_;
	}
	
	
	
	public boolean isUser(String usuario) {
		for (Usuario u : jugadores_) {
			if(u.getMail().equals(usuario)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public int getnJugadores_() {
		return nJugadores_;
	}
	public void setnJugadores_(int nJugadores_) {
		this.nJugadores_ = nJugadores_;
	}
	
	public Hilo[] gethilos_() {
		return hilos_;
	}
	
	public Hilo[] mostrarTodo() {
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		Usuario u = new Usuario();
		for (Hilo h : hilos_) {
			h.setPartida_(null);
			Usuario u2 = h.getJugadorInicial_();
			u2.setNull();
			h.setJugadorInicial_(u2);
			
			respuestas = h.getRespuestas_();
			for(Respuesta r : respuestas) {
				u = r.getAutor_();
				u.setNull();
				r.setAutor_(u);
				r.setDibujo(null);
			}
			h.setRespuestas_(respuestas);
		}
		return hilos_;
	}
	public String getNombre() {
		return nombre_;
	}
	public void setNombre(String nombre) {
		this.nombre_ = nombre;
	}
	public void setTurno(int i) {
		this.turno_=i;
	}
	public int getTurno() {
		return this.turno_;
	}
	
	private void opTurno() {
		boolean avanzar = true;
		for (Hilo h : hilos_) { //Comprobar que se ha jugado cada hilo
			if(h.getSize() <= turno_) {
				avanzar=false; //Si queda algun hilo restante
			}
		}
		if(avanzar) {
			if(turno_==nJugadores_-1) {
				//La partida ha acabado
				this.estado_=DemoApplication.VOTANDO;
				return;
			}
			turno_++; //AVISAR A LOS JUGADORES
		
			
			
			
			
			    
			
		}
		
		
	}
	
	public void empezarPartida() {
		this.hilos_ = new Hilo[nJugadores_]; //Construimos los hilos
		int i=0;
		for (Usuario u : jugadores_) {
				hilos_[i]= new Hilo(u,this); //Inicializamos los hilos
				i++;
		}
		this.estado_= DemoApplication.JUGANDO;
		this.turno_=0;
		
	}

	public boolean turnoJugado(String idUser) {
		int i = getHiloJugador(idUser);
		System.out.println("Hilo del jugador:"+i);
		int j = (i+turno_)%nJugadores_;
		System.out.println("Hilo de este turno:"+j);
		//System.out.println(hilos_[j].getSize());
		if(hilos_[j].getSize()>turno_) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
}
