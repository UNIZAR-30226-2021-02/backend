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
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(identificador)) {
				return p.isVotadoGracioso();
				
			}
		}
		return false;
	}
	
	public boolean votadoListo(int idPartida, String identificador) {
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(identificador)) {
				return p.isVotadoListo();
				
			}
		}
		return false;
	}
	
	public boolean votadoDibujo(int idPartida, String identificador) {
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(identificador)) {
				return p.isVotadoDibujo();
				
			}
		}
		return false;
	}
	
	public boolean addPuntosDibujo(int idPartida, String idUsuario, String identificador) {
		int bien = 0;
		Puntos p1 = null,p2 = null;
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(idUsuario)) {
				p1 = p;
				bien++;
			}if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(identificador)){
				p2 = p;
				bien++;
			}
			if (bien==2) {
				System.out.println("añado dibujo");
				p1.sumarPDibujo(1);
				p2.setVotadoDibujo(true);
				return true;
			}
		}
		return false;
	}
	
	public boolean addPuntosListo(int idPartida, String idUsuario, String identificador) {
		int bien = 0;
		Puntos p1 = null,p2 = null;
		for (Puntos p : puntos_) {
			System.out.println(p.getIdPartida_());
			System.out.println(p.getIdUsuario_().getMail());
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(idUsuario)) {
				p1 = p;
				bien++;
			}if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(identificador)){
				p2 = p;
				bien++;
			}
			if (bien==2) {
				System.out.println("añado listo");
				p1.sumarPListo(1);
				p2.setVotadoListo(true);
				return true;
			}
		}
		return false;
	}
	
	public boolean addPuntosGracioso(int idPartida, String idUsuario, String identificador) {
		int bien = 0;
		Puntos p1 = null,p2 = null;
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(idUsuario)) {
				p1 = p;
				bien++;
			}if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(identificador)){
				p2 = p;
				bien++;
			}
			if (bien==2) {
				System.out.println("añado gracioso");
				p1.sumarPGracioso(1);
				p2.setVotadoGracioso(true);
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public List<Puntos> getPuntosPartida(int idPartida,String identificador){
		List<Puntos> respuesta = new ArrayList<>();
		Usuario u = new Usuario();
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
				u = usuarioRepo.findByMail(p.getIdUsuario_().getMail());
				u.setNull();
				p.setIdUsuario_(u);
				respuesta.add(p);
			}
		}
		return respuesta;
	}
	
	
	
	public Puntos getPuntosJugador(int idPartida, String idUsuario) {
		Usuario u = new Usuario();
		for (Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida && p.getIdUsuario_().getMail().equals(idUsuario)) {
				p.setConsultado(true);
				u = usuarioRepo.findByMail(p.getIdUsuario_().getMail());
				u.setNull();
				p.setIdUsuario_(u);
				return p;
			}
		}
		return null;
	}

	public void ini(Partida p) {
		int idPartida = p.getId();
		List<Usuario> jugadores = p.getJugadores_();
		for(Usuario u : jugadores) {
			Puntos puntos = new Puntos(idPartida,u);
			System.out.println(puntos.getIdPartida_());
			System.out.println(puntos.getIdUsuario_().getMail());
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
		List<Puntos> borrados = new ArrayList<Puntos>();
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida) {
				Usuario u = usuarioRepo.findByMail(p.getIdUsuario_().getMail());
				System.out.println(p.getIdUsuario_());
				u.setpGracioso(u.getpGracioso()+p.getpGracioso_());
				u.setpListo(u.getpListo()+p.getpListo_());
				u.setpDibujo(u.getpDibujo()+p.getpDibujo_());
				u.setEstrellas(u.getEstrellas()+p.calcularEstrellas());
				u.setMonedas(u.getMonedas()+p.calcularMonedas());
				usuarioRepo.save(u);
				borrados.add(p);			
			}
		}
		puntos_.removeAll(borrados);
		for(Puntos p1 : puntos_) {
			System.out.println(p1.getIdUsuario_()+"--"+p1.getIdPartida_());
		}
	}
	
	public boolean votadoJugador(int idPartida, String identificador) {
		for(Puntos p : puntos_) {
			if(p.getIdPartida_()==idPartida&&p.getIdUsuario_().getMail().equals(identificador)) {
				return p.votadoTodo();
			}
		}
		return false;
	}
	
}
