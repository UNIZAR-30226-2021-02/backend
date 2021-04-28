package com.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.demo.model.Hilo;
import com.demo.model.Invitaciones;
import com.demo.model.Partida;
import com.demo.model.Respuesta;
import com.demo.model.RespuestaFront;
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
		Usuario host = usuarioRepo.findByMail(identificador);
		Partida partida = new Partida(host,partidaPar.getNombre());
		partidaRepo.save(partida);
		//System.out.println(partida.getHost_());
		partidaRepo.save(partida);
		return partidaRepo.findById(partida.getId());
	}
	
	public boolean addJugador(String identificador, int id) {
		Partida partida = partidaRepo.findById(id);
			if(partida != null && partida.getEstado_().equals(DemoApplication.ESPERANDO)) {
				if(partida.getnJugadores_()<DemoApplication.MAX_JUGADORES) {
					if(! partida.isUser(identificador)) {
						Usuario u = usuarioRepo.findByMail(identificador);
						partida.addJugador(u);
											
						
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
		Usuario u = usuarioRepo.findByMail(identificador);
		List<Partida> respuesta = new ArrayList<Partida>();
		for (Partida p : u.getPartidas()) {
			p.setNull();
			respuesta.add(p);
		}
		return respuesta;
	}
	
	
	public boolean inviteGame(String idInvitado,String identificador, int idPartida) {
		
		Usuario u = usuarioRepo.findByMail(idInvitado);
		Usuario invitador = usuarioRepo.findByMail(identificador);
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
		
		Usuario u = usuarioRepo.findByMail(identificador);
		
		Partida p = partidaRepo.findById(idPartida);
		
		Invitaciones i = invitacionesRepo.findByPartidaAndInvitado(p,u);
		if(i == null) {
			System.out.println("Sejodio");
		}
		invitacionesRepo.delete(i);
	
	}
	
	public List<Invitaciones> getInvitacionesJugador (String identificador){
		Usuario u = usuarioRepo.findByMail(identificador);
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
	
	public List<Usuario> listPlayers(int idPartida){
		
		
		Partida p = partidaRepo.findById(idPartida);
		List<Usuario> respuesta = p.getJugadores_();
		
		for(Usuario u : respuesta ) {
			System.out.println("USUARIO = "+u);
			u.setPassword(null);
			u.setNull();
		}
		return respuesta;
	}

	
	public List<Usuario> listPlayersGame(int idPartida, String identificador){
		
		Partida p = partidaRepo.findById(idPartida);
		Usuario u = usuarioRepo.findByMail(identificador);
		List<Usuario> amigos = u.getAmigo();
		List<Usuario> respuesta = new ArrayList<>();
		for(Usuario a: amigos) {
			if(!p.isUser(a.getMail())) {
				a.setNull();
				respuesta.add(a);
			}
		
		}
		return respuesta;
	}


	public int startGame(String identificador, int idPartida) {
		// TODO Auto-generated method stub
		Partida p = partidaRepo.findById(idPartida);
		if(p.getHost_().getMail().equals(identificador)) {
			//Eres el host
			if(p.getEstado_().equals(DemoApplication.ESPERANDO)) {
				//Esta sin empezar
				p.empezarPartida();
				partidaRepo.save(p);
				invitacionesRepo.deleteAll(invitacionesRepo.findByPartida(p)); //Eliminamos invitaciones pendientes
				//Notificar a todos de que ha empezado 
				return 0;
			}
			else {
				//Ya está empezada
				return 1;
			}
		}else {
			//No eres el host
			return 2;
		}
	}
	
	public boolean addRespuesta(int idPartida, String autor,byte[] contenido,boolean dibujo){
		
		System.out.println(contenido);
		Partida p=partidaRepo.findById(idPartida);
		Usuario u = usuarioRepo.findByMail(autor);
		Respuesta r = new Respuesta(u,contenido,dibujo);
		Hilo h = p.addRespuesta(u, r);
		if(h==null) {
			return false;
		}else {
			r.setHilo(h);
			respuestaRepo.save(r);
			hiloRepo.save(h);
			partidaRepo.save(p);
			return true;
		}
	}
	
	public byte[] getImageResponse(int idFoto) {
		return respuestaRepo.findById(idFoto).getContenido_();
	}
	
	
	public RespuestaFront getResponse(String identificador, int idPartida) {
		RespuestaFront respuesta;
		Partida p = partidaRepo.findById(idPartida);
		System.out.println(p.getTurno());
		if (p.getEstado_().equals(DemoApplication.VOTANDO)) {
			//La fase de turnos ha acabado
			return new RespuestaFront(-3,false,null);
		}else if (p.getTurno()==0) {
			System.out.println("Turno 0");
			//Primer turno
			return new RespuestaFront(-1,false,null);
		}else if(p.turnoJugado(identificador)) {
			//Ya has jugado este turno
			return new RespuestaFront(-2,false,null);
		}else {
		Usuario u = usuarioRepo.findByMail(identificador);
		Hilo h = p.getHiloRespuesta(u);
		List <Respuesta> listaR = h.getRespuestas_();
		Respuesta r = listaR.get(listaR.size()-1); 
		boolean esDibujo = r.isEsDibujo();
		System.out.println(r.getId_()+"---"+r.getContenido_()+"---"+r.isEsDibujo());
		 if(esDibujo) {
			 respuesta= new RespuestaFront(r.getId_(),true,null);
		 }
		 else {
			 respuesta= new RespuestaFront(r.getId_(),false,r.getFrase());
		 }
		 
		return respuesta;
		}
	}

	public Hilo[] getAllRespuestas(int idPartida) {
		Partida p = partidaRepo.findById(idPartida);
		return p.mostrarTodo();
	}
}
