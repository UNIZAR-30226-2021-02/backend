package com.demo.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.DemoApplication;
import com.demo.model.Partida;
import com.demo.model.Puntos;
import com.demo.model.Usuario;

@Repository
public class PuntosRepo {
	
	private List<Puntos> puntos_ = new ArrayList<>();
	
	@Autowired
	UsuarioRepo usuarioRepo;
	
	public boolean votadoGracioso(int idPartida, String identificador) {
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(identificador)) {
				return p.isVotadoGracioso();
				
			}
		}
		return false;
	}
	
	public boolean votadoListo(int idPartida, String identificador) {
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(identificador)) {
				return p.isVotadoListo();
				
			}
		}
		return false;
	}
	
	public boolean votadoDibujo(int idPartida, String identificador) {
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(identificador)) {
				return p.isVotadoDibujo();
				
			}
		}
		return false;
	}
	
	public boolean addPuntosDibujo(int idPartida, String idUsuario, String identificador) {
		int bien = 0;
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(idUsuario)) {
				p.sumarPDibujo(1);
				bien++;
			}if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(identificador)){
				p.setVotadoDibujo(true);
				bien++;
			}
		}
		return bien==2;
	}
	
	public boolean addPuntosListo(int idPartida, String idUsuario, String identificador) {
		int bien = 0;
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(idUsuario)) {
				p.sumarPListo(1);
				bien++;
			}if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(identificador)){
				p.setVotadoListo(true);
				bien++;
			}
		}
		return bien==2;
	}
	
	public boolean addPuntosGracioso(int idPartida, String idUsuario, String identificador) {
		int bien = 0;
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(idUsuario)) {
				p.sumarPGracioso(1);
				bien++;
			}if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(identificador)){
				p.setVotadoGracioso(true);
				bien++;
			}
		}
		return bien==2;
	}
	
	
	
	public List<Puntos> getPuntosPartida(int idPartida,String identificador){
		List<Puntos> respuesta = new ArrayList<>();
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
				if(identificador.equals(p.getIdUsuario_())) {
					p.setConsultado(true);
				}
				respuesta.add(p);
			}
		}
		return respuesta;
	}
	
	
	
	public Puntos getPuntosJugador(int idPartida, String idUsuario) {
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().equals(idUsuario)) {
				return p;
			}
		}
		return null;
	}

	public void ini(Partida p) {
		int idPartida = p.getId();
		List<Usuario> jugadores = p.getJugadores_();
		for(Usuario u : jugadores) {
			Puntos puntos = new Puntos(idPartida,u.getMail());
			puntos_.add(puntos);
		}
	}
	
	public boolean todosVotado (int idPartida) {
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
				if(!p.votadoTodo()) {
					System.out.println(p.votadoTodo());
					return false;
				}
			}
		}
		System.out.println("Toca funar");
		return true;
	}
	
	public boolean todosConsultado(int idPartida) {
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
				if(!p.isConsultado()) {
					System.out.println(p.votadoTodo());
					return false;
				}
			}
		}
		System.out.println("Toca funar puntos");
		return true;
		
	}
	
	public void delete(int idPartida) {
		for(Puntos p : puntos_) {
			System.out.println(p.getIdUsuario_()+"--"+p.getIdPartida_());
		}
		Iterator it = puntos_.iterator();
		while(it.hasNext()) {
			Puntos p = (Puntos) it.next();
			if(p.getIdPartida_()==idPartida) {
				System.out.println(p.getIdUsuario_());
				Usuario u = usuarioRepo.findByMail(p.getIdUsuario_());
				u.setpGracioso(u.getpGracioso()+p.getpGracioso_());
				u.setpListo(u.getpListo()+p.getpListo_());
				u.setpDibujo(u.getpDibujo()+p.getpDibujo_());
				u.setEstrellas(u.getEstrellas()+p.calcularEstrellas());
				u.setMonedas(u.getMonedas()+p.calcularMonedas());
				usuarioRepo.save(u);
				Puntos p2 = p;
				System.out.println("ME VOY A FUNAR A :"+p.getIdUsuario_()+"--"+p.getIdPartida_());
				puntos_.remove(p2);
				for(Puntos p1 : puntos_) {
					System.out.println("Queda:"+p1.getIdUsuario_()+"--"+p1.getIdPartida_());
				}
			}else {
				System.out.println(p.getIdUsuario_()+"---"+p.getIdPartida_());
			}
			
		}/*
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
				System.out.println("ME VOY A FUNAR A :"+p.getIdUsuario_()+"--"+p.getIdPartida_());
				puntos_.remove(p);
			}
		}*/
		for(Puntos p : puntos_) {
			System.out.println(p.getIdUsuario_()+"--"+p.getIdPartida_());
		}
	}
	
	public boolean votadoJugador(int idPartida, String identificador) {
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida&&p.getIdUsuario_().equals(identificador)) {
				return p.votadoTodo();
			}
		}
		return false;
	}
	
}
