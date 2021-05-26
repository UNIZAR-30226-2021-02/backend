package com.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.demo.model.Foto;
import com.demo.model.Hilo;
import com.demo.model.Invitaciones;
import com.demo.model.Partida;
import com.demo.model.Puntos;
import com.demo.model.Respuesta;
import com.demo.model.Usuario;
import com.demo.repository.*;

import com.demo.DemoApplication;


@Service
public class GameService {
	
	
	@Autowired
	private static HashMap<Integer,MyThread > threads = new HashMap<Integer, MyThread>();
	
	
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
	@Autowired
	private PuntosRepo puntosRepo;
	@Autowired
	private FotoRepo fotoRepo;
	
	
	
	
	public void ponerTimer(int idPartida) {
			MyThread t = threads.get(idPartida);
			if(t!=null) {
				System.out.println("Despertamos el thread");
				t.stop();
			}
			t = new MyThread("nombre",idPartida);
			threads.put(idPartida, t);	
		}
	

	
	public Partida crearPartida(String identificador,Partida partidaPar) {
		Usuario host = usuarioRepo.findByMail(identificador);
		Partida partida = new Partida(host,partidaPar.getNombre());
		
		
		partidaRepo.save(partida);
		//System.out.println(partida.getHost_());
		partidaRepo.save(partida);
		int idPartida = partida.getId();
		
		return partidaRepo.findById(idPartida);
	}
	
	
	
	
	
	public boolean addJugador(String identificador, int id) {
		Partida partida = partidaRepo.findById(id);
			if(partida != null && partida.getEstado_().equals(DemoApplication.ESPERANDO)) {
				if(partida.getnJugadores_()<DemoApplication.MAX_JUGADORES) {
					if(! partida.isUser(identificador)) {
						Usuario u = usuarioRepo.findByMail(identificador);
						partida.addJugador(u);
						Invitaciones i = invitacionesRepo.findByPartidaAndInvitado(partida,u);
						//System.out.println(i);
						partidaRepo.save(partida);
						invitacionesRepo.delete(i);
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
			System.out.println("No existe esa invitacion");
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
		System.out.println("el usuario "+u.getNombre()+" tiene "+u.getnAmigos()+" amigos");
		System.out.println("La lista de amigos tiene size:"+amigos.size());
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
		if(p.getHost_().getMail().equals(identificador) && p !=null) {
			//Eres el host
			if(p.getEstado_().equals(DemoApplication.ESPERANDO) && p.getnJugadores_() >= DemoApplication.MIN_JUGADORES) {
				//Esta sin empezar
				p.empezarPartida();
				partidaRepo.save(p);
				ponerTimer(idPartida);
				invitacionesRepo.deleteAll(invitacionesRepo.findByPartida(p)); //Eliminamos invitaciones pendientes
				//Notificar a todos de que ha empezado 
				return 0;
			}else if (p.getnJugadores_() < DemoApplication.MIN_JUGADORES){
				//No se ha alcanzado el mínimo de jugadores
				return 3;
			}
			else {
				//Ya está empezada
				return 1;
			}
		}else {
			//No eres el host o has metido una partida que no es
			return 2;
		}
	}
	
	public boolean addRespuesta(int idPartida, String autor,byte[] contenido,boolean dibujo, String frase){
		Respuesta r;
		Partida p=partidaRepo.findById(idPartida);
		Usuario u = usuarioRepo.findByMail(autor);
		r= new Respuesta(u,contenido,dibujo,frase);
		int turnoAntes = p.getTurno();
		if((p.getTurno()%2==0 && dibujo) || (p.getTurno()%2==1 && !dibujo)) {
			return false;
		}
		Hilo h = p.addRespuesta(u, r);
		int turnoDespues = p.getTurno();
		
		if(turnoDespues > turnoAntes) {
			System.out.println("Ponemos timer en partida: "+idPartida);
			ponerTimer(p.getId());
		}if(h==null) {
			return false;
		}else {
			r.setHilo(h);
			respuestaRepo.save(r);
			hiloRepo.save(h);
			if(p.getEstado_().equals(DemoApplication.VOTANDO)&&!p.isIni()) {
				System.out.println("Inicializamos puntos de la partida: "+idPartida);
				p.setIni(true);
				puntosRepo.ini(p);
			}
			partidaRepo.save(p);
			return true;
		}
		
		
	}
	
	public byte[] getImageResponse(int idFoto) {
		return respuestaRepo.findById(idFoto).getDibujo();
	}
	
	
	public Respuesta getResponse(String identificador, int idPartida) {
		Partida p = partidaRepo.findById(idPartida);
		if(p.getEstado_().equals(DemoApplication.ESPERANDO)) {
			//No esta empezada
			return null;
		}
		if(p.getEstado_().equals(DemoApplication.VOTANDO)&& puntosRepo.votadoJugador(idPartida,identificador)) {
			return new Respuesta(-4);
		}
		else if (p.getEstado_().equals(DemoApplication.VOTANDO)) {
			//La fase de turnos ha acabado
			return new Respuesta(-3);
		}else if (p.getTurno()==0 && !p.turnoJugado(identificador)) {
			System.out.println("Turno 0");
			//Primer turno
			return new Respuesta(-1);
		}else if(p.turnoJugado(identificador)) {
			//Ya has jugado este turno
			return new Respuesta(-2);
		}else {
		Usuario u = usuarioRepo.findByMail(identificador);
		Hilo h = p.getHiloRespuesta(u);
		List <Respuesta> listaR = h.getRespuestas_();
		Respuesta r = listaR.get(listaR.size()-1); 
		//System.out.println(r.getId_()+"---"+"---"+r.isEsDibujo());
		r.setDibujo(null); 
		r.setAutor_(null);
		return r;
		}
	}

	public Hilo[] getAllRespuestas(int idPartida) {
		Partida p = partidaRepo.findById(idPartida);
		return p.mostrarTodo();
	}

	public boolean votarGracioso(int idPartida, String identificador, String votado) {
		
		Partida p = partidaRepo.findById(idPartida);
		if (p.getEstado_().equals(DemoApplication.VOTANDO) && !puntosRepo.votadoGracioso(idPartida, identificador)) {
			boolean resultado=puntosRepo.addPuntosGracioso(idPartida, votado,identificador);
			if(puntosRepo.todosVotado(idPartida)) {
				//p.setEstado_(DemoApplication.ACABADA);
				partidaRepo.save(p);
			}
			return resultado;
		}else {
			return false;
		}
}
				
	

	public boolean votarListo(int idPartida, String identificador, String votado) {
		Partida p = partidaRepo.findById(idPartida);
		if (p.getEstado_().equals(DemoApplication.VOTANDO) && !puntosRepo.votadoListo(idPartida, identificador)) {
			boolean resultado=puntosRepo.addPuntosListo(idPartida, votado,identificador);
			if(puntosRepo.todosVotado(idPartida)) {
				//p.setEstado_(DemoApplication.ACABADA);
				partidaRepo.save(p);
			}
			return resultado;
		}else {
			return false;
		}
	}

	public boolean votarDibujo(int idPartida, String identificador, String votado) {
		Partida p = partidaRepo.findById(idPartida);
		if (p.getEstado_().equals(DemoApplication.VOTANDO) && !puntosRepo.votadoDibujo(idPartida, identificador)) {
			boolean resultado=puntosRepo.addPuntosDibujo(idPartida, votado,identificador);
			if(puntosRepo.todosVotado(idPartida)) {
				//p.setEstado_(DemoApplication.ACABADA);
				partidaRepo.save(p);
			}
			return resultado;
		}else {
			return false;
		}
	}

	public List<Integer> puntosJugador(int idPartida, String identificador) {
		Usuario u2 = usuarioRepo.findByMail(identificador);
		//System.out.println(u2);
		if(puntosRepo.todosVotado(idPartida) && partidaRepo.findById(idPartida) != null) {
			Puntos p = puntosRepo.getPuntosJugador(idPartida, identificador);
			List<Integer> resp = new ArrayList<>();
			if(puntosRepo.todosConsultado(idPartida)) {
				partidaRepo.deleteRespuestasPartida(idPartida);
				partidaRepo.deleteJugadoresPartida(idPartida);
				partidaRepo.deleteHilosPartida(idPartida);
				partidaRepo.deletePartida(idPartida);
				//Partida par = partidaRepo.findById(idPartida);
				//par.setEstado_(DemoApplication.ACABADA);
				//partidaRepo.save(par);
				puntosRepo.delete(idPartida);
			}if(p !=null) {
				resp.add(p.calcularEstrellas());
				resp.add(p.calcularMonedas());
			}
			u2.setPuntos(u2.getpGracioso()+p.getpGracioso_(), u2.getpListo()+p.getpListo_(), u2.getpDibujo()+p.getpDibujo_(),
					     u2.getEstrellas()+p.calcularEstrellas(), u2.getMonedas()+p.calcularMonedas());
			
			usuarioRepo.save(u2);
			return resp;	
		}else {
			usuarioRepo.save(u2);
			return null;
		}
		
	}
	
	public List<Puntos> puntosPartida(int idPartida,String identificador) {
		if(/*puntosRepo.todosVotado(idPartida) &&*/ partidaRepo.findById(idPartida) != null) { //Si han votado todos
			
			List<Puntos> p = puntosRepo.getPuntosPartida(idPartida,identificador);
			return p;
		}else {
			//no han votado todos o nos hemos funao la partida
			return null;
		}
	}
	
	public void resetVotos(int idPartida) {
		Partida p = partidaRepo.findById(idPartida);
		puntosRepo.ini(p);
	}
	
	
	
	
	public List<Foto> listarFotos(){
		return fotoRepo.findAll();
	}
	
	public List<Foto> listarFotosCompradas(String identificador){
		Usuario u = usuarioRepo.findByMail(identificador);
		return u.getFotos();
	}
	
	public List<Foto> listarFotosBloqueadas(String identificador){
		Usuario u = usuarioRepo.findByMail(identificador);
		List<Foto> compradas =  u.getFotos();
		List<Foto> todas = fotoRepo.findAll();
		List<Foto> respuesta = new ArrayList<>();
		for(Foto f : todas) {
			if(!compradas.contains(f)) {
				respuesta.add(f);
			}
		}
		return respuesta;
	}
	
	public boolean comprarFoto(String identificador,String idFoto) {
		Usuario u = usuarioRepo.findByMail(identificador);
		Foto f = fotoRepo.findByIdFoto(idFoto);
		
		if(u.getMonedas()>=f.getPrecio()&&u.noComprada(f)) {
			u.setMonedas(u.getMonedas()-f.getPrecio());
			u.addFoto(f);
			usuarioRepo.save(u);
			return true;
		}
		else return false;
		
	}
	
	public boolean cambiarFoto(String identificador, String idFoto) {
		Usuario u = usuarioRepo.findByMail(identificador);
		Foto f = fotoRepo.findByIdFoto(idFoto);
		if(!u.noComprada(f)) {
			u.setFotPerf(idFoto);
			usuarioRepo.save(u);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public void ponerRespuestasDefault(int idPartida) {
		Partida p = partidaRepo.findById(idPartida);
		for(Usuario u: p.getJugadores_()) {
			if(!p.turnoJugado(u.getMail())) {
				System.out.println(u.getNombre()+" inserta respuestaDefault en partida: " +p.getNombre()+ " con id:"+idPartida);
				if(p.getTurno()%2==0) {
					addRespuesta(p.getId(),u.getMail(),null,false,"Nadie ha respondido");
				}else {	
					addRespuesta(p.getId(),u.getMail(),null,true,null);
				}
			}
		}	
	}
	
	@PostConstruct
	public void poblarFotos() {
		
		Foto f0 = new Foto(0,"foto0.png");
		Foto f1 = new Foto(3,"foto1.png");
		Foto f2 = new Foto(50,"foto2.png");
		Foto f3 = new Foto(100,"foto3.png");
		Foto f4 = new Foto(200,"foto4.png");
		
		fotoRepo.save(f0);
		fotoRepo.save(f1);
		fotoRepo.save(f2);
		fotoRepo.save(f3);
		fotoRepo.save(f4);
	}
	
	
	
	
	public class MyThread implements Runnable {
		  
		@Autowired
		private ApplicationContext applicationContext;
	     @Autowired
	     private GameService game;
				
		
	    private String name;
	    Thread t;
	    private int idPartida;
	    MyThread(String threadname,int idPartida)
	    {
	        
	    	this.idPartida = idPartida;
	    	name = threadname;
	        t = new Thread(this, name);
	        System.out.println("New thread: " + t);
	       
	        t.start(); // Starting the thread
	    }
	  
	   
	    
	    // execution of thread starts from run() method
	    public void run()
	    {
	    	long delay = 86400000;
			long delayPrueba = 600000;
	        try {
				Thread.sleep(delayPrueba);
				System.out.println("TIEMPO TERMINADO PARTIDA:" +idPartida);
				ponerRespuestasDefault(idPartida);
			} catch (InterruptedException e) {
				System.out.println("Nos lo hemos funao");
			}
	    }
	  
	    // for stopping the thread
	    public void stop()
	    {
	    	t.interrupt();
	    }
	}

	
}
