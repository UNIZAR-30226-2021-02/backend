package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.model.Partida;
import com.demo.model.Usuario;
@Service
public class GameService {

	private List<Partida> Partidas_ = new ArrayList<Partida>();
	
	//Buscar partida por host
	public void crearPartida(Usuario host) {
		Partida aux = new Partida(host);
		this.Partidas_.add(aux);
		System.out.println(aux.getHost_());
		System.out.println(aux.getId());
	}
	
	
}
