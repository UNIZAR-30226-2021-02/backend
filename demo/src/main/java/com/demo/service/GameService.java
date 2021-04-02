package com.demo.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;


import com.demo.model.Partida;
import com.demo.model.Usuario;
import com.demo.DemoApplication;

@Service
public class GameService {
	
	
	

	
	private List<Partida> partidas_ = new ArrayList<Partida>();
	private int proxId = 0;
	//Buscar partida por host
	public void crearPartida(Usuario host) {
		Partida aux = new Partida(host,proxId);
		proxId++;
		this.partidas_.add(aux);
		System.out.println(aux.getHost_());
		System.out.println(aux.getId());
	}
	
	public boolean addJugador(Usuario usuario, int id) {
		
		for (Partida p : partidas_) {
			if(p.getId() == id) {
				if(p.getNumJugadores()<DemoApplication.MAX_JUGADORES) {
				
					p.addJugador(usuario);
					return true;
				}
			}
		}
		
		return false;
		
	}

	public Partida getPartida(int idPartida) {

		for (Partida p : partidas_) {
			if(p.getId() == idPartida) {
				return p;
			}
		}
		return null;
	}

	
}
