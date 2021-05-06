package com.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.model.Partida;
import com.demo.model.Puntos;
import com.demo.model.Usuario;

@Repository
public class PuntosRepo {
	
	private List<Puntos> puntos_ = new ArrayList<>();
	
	
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
	
	
	
	public List<Puntos> getPuntosPartida(int idPartida){
		List<Puntos> respuesta = new ArrayList<>();
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
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
	
}
