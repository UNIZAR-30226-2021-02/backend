package com.demo.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Hilo;
import com.demo.model.Invitaciones;
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
	@Autowired
	private InvitacionesRepo invitacionesRepo;
	

	
	public Partida crearPartida(String identificador,Partida partidaPar) {
		Usuario host = usuarioRepo.findByNombre(identificador);
		Hilo hiloAux = new Hilo(host);
		Partida partida = new Partida(host,partidaPar.getNombre());
		partidaRepo.save(partida);
		hiloAux.setPartida_(partida);
		hiloRepo.save(hiloAux);
		
		partida.addHilo(hiloAux);
		//System.out.println(partida.getHost_());
		partidaRepo.save(partida);
		return partidaRepo.findById(partida.getId());
	}
	
	public boolean addJugador(String identificador, int id) {
		Partida partida = partidaRepo.findById(id);
			if(partida != null && partida.getEstado_().equals(DemoApplication.ESPERANDO)) {
				if(partida.getnJugadores_()<DemoApplication.MAX_JUGADORES) {
					if(! partida.isUser(identificador)) {
						Hilo hiloAux = new Hilo(usuarioRepo.findByNombre(identificador));
						hiloAux.setPartida_(partida);
						hiloRepo.save(hiloAux);
						partida.addHilo(hiloAux);
						partida.addJugador(usuarioRepo.findByNombre(identificador));
						
						Usuario u = usuarioRepo.findByNombre(identificador);
						
						Invitaciones i = invitacionesRepo.findByPartidaAndInvitado(partida,u);
						System.out.println(i);
						
						invitacionesRepo.delete(i);
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
	
	
	public boolean inviteGame(String idInvitado,String identificador, int idPartida) {
		
		Usuario u = usuarioRepo.findByNombre(idInvitado);
		Usuario invitador = usuarioRepo.findByNombre(identificador);
		Partida p = partidaRepo.findById(idPartida);
		
		if(p!=null&&u!=null&&invitador!=null) {
			if(p.isUser(idInvitado)|| null!=invitacionesRepo.findByPartidaAndInvitado(p, u) || !u.esAmigoDe(invitador)) {
				//Ya estaba en la partida o estaba invitado
				return false;
			}
			else {
				Invitaciones i = new Invitaciones();
				i.setInvitado(u);
				i.setInvitador(invitador);
				i.setPartida(p);		
				invitacionesRepo.save(i);
				
				return true;		
			}			
		}
		return false;
		
		
		
	}
	
	
	public void denyInvite(String identificador,int idPartida) {
		
		Usuario u = usuarioRepo.findByNombre(identificador);
		
		Partida p = partidaRepo.findById(idPartida);
		
		Invitaciones i = invitacionesRepo.findByPartidaAndInvitado(p,u);
		if(i == null) {
			System.out.println("Sejodio");
		}
		invitacionesRepo.delete(i);
	
	}
	
	public List<Invitaciones> getInvitacionesJugador (String identificador){
		Usuario u = usuarioRepo.findByNombre(identificador);
		List<Invitaciones> lista = invitacionesRepo.findByInvitado(u);
		List<Invitaciones> respuesta = new ArrayList<>();
		if(lista==null) {
			return null;
		}
		for(Invitaciones i:lista) {
			i.setInvitadorNull();
			i.setPartidaNull();
			
			i.setInvitado(null);
			respuesta.add(i);
			
		}
		return respuesta;
	}
	
	
}
