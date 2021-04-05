package com.demo.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Hilo;
import com.demo.model.Partida;
import com.demo.model.Usuario;
import com.demo.repository.*;

import com.demo.DemoApplication;

@Service
public class GameService {
	
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	@Autowired
	private HiloRepo hiloRepo;
	@Autowired
	private PartidaRepo partidaRepo;
	@Autowired
	private RespuestaRepo respuestaRepo;
	

	
	public void crearPartida(String identificador) {
		Usuario host = usuarioRepo.findByNombre(identificador);
		Hilo hiloAux = new Hilo(host);
		Partida partida = new Partida(host);
		partidaRepo.save(partida);
		hiloAux.setPartida_(partida);
		hiloRepo.save(hiloAux);
		
		partida.addHilo(hiloAux);
		//partida.addJugador(host);
		System.out.println(partida.getHost_());
		partidaRepo.save(partida);
	}
	
	public boolean addJugador(String identificador, int id) {
		Partida partida = partidaRepo.findById(id);
			if(partida != null && partida.getEstado_().equals(DemoApplication.ESPERANDO)) {
				if(partida.getNumJugadores()<DemoApplication.MAX_JUGADORES) {
					if(! partida.isUser(identificador)) {
						Hilo hiloAux = new Hilo(usuarioRepo.findByNombre(identificador));
						hiloAux.setPartida_(partida);
						hiloRepo.save(hiloAux);
						partida.addHilo(hiloAux);
						partida.addJugador(usuarioRepo.findByNombre(identificador));
						partidaRepo.save(partida);
						System.out.println("Añadido correctamente");
						return true;
					}else {
						System.out.println("Error, ya estas en esta partida");
						return false;
					}
				}else {
					System.out.println("Error, no caben más jugadores");
					return false;
				}
			}else {
				System.out.println("La partida no existe o está empezada");
			}
		
		return false;
		
	}

	public Partida getPartida(int idPartida) {
		return partidaRepo.findById(idPartida);
		
	}

	public List<Partida> getPartidasJugador(String identificador){
		Usuario u = usuarioRepo.findByNombre(identificador);
		List<Partida> respuesta = new ArrayList<Partida>();
		for (Partida p : u.getPartidas()) {
			p.setNull();
			respuesta.add(p);
		}
		return respuesta;
	}
}
