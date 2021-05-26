package com.demo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import com.demo.controller.AuthController;
import com.demo.fcm.Note;
import com.demo.model.Foto;
import com.demo.model.Hilo;
import com.demo.model.Invitaciones;
import com.demo.model.Nota;
import com.demo.model.Partida;
import com.demo.model.Puntos;
import com.demo.model.Respuesta;
import com.demo.model.Usuario;

import com.demo.repository.TokenRepo;
import com.demo.repository.UsuarioRepo;
import com.demo.service.GameService;
import com.demo.service.NotificationService;
import com.demo.service.UserService;


@CrossOrigin(origins = "*",methods={RequestMethod.POST,RequestMethod.GET})
@RestController
@RequestMapping(value = "/api")
public class RestDemoController {

	
	@Autowired
	private NotificationService notificationService;
	
	
	@Autowired
	private AuthController jwt;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	
	@Autowired 
	private UsuarioRepo usuarioRepo;
	

	
	@Autowired
	private UserService service;
	
	@Autowired
	private GameService game;


	//YA NO SE USA
	@GetMapping(value = "/all")
	public List<Usuario> listar(){
		return usuarioRepo.findAll();
	}
	

	//YA NO SE USA
	@PostMapping(value="/prueba")
	public void probar() {
		
	}

	
	@PostMapping(value = "/register")
	public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
		usuario.printUser();
		Usuario u = new Usuario();
		String nombreUsuario = usuario.getNombre();
		String mail = usuario.getMail();
		if(usuarioRepo.findByMail(mail)==null&&usuarioRepo.findByNombre(nombreUsuario)==null) {
			u.setNombre(nombreUsuario);
			u.setMail(mail);
			  MessageDigest digest = null;
			try {
				digest = MessageDigest.getInstance("SHA-512");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  digest.reset();
			  try {
				digest.update(usuario.getPassword().getBytes("utf8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  String toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
			u.setPassword(toReturn);
			u.setRole("USER");
			u.setnAmigos(0);
			u.setPuntos(new Integer(0),new Integer(0),new Integer(0),new Integer(0),new Integer(0));
			u.setFotPerf("foto0.png");
			u.setToken(usuario.getToken());
			usuarioRepo.save(u);
			game.comprarFoto(mail,"foto0.png");
			System.out.println("El usuario no existe, se crea");
			
			String token = jwt.getJWTToken(mail);
			u.setToken(token);
			return new ResponseEntity<Usuario>(u,HttpStatus.CREATED);
		
		}
		else {
			System.out.println("El usuario ya existe, no lo creamos");
			
			return new ResponseEntity<Usuario>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	

	
	@PostMapping(value = "/login")
	public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
	
		Usuario u = usuarioRepo.findByMail(usuario.getMail());
		System.out.println(usuario.getPassword());
		if(u !=null){
			

			  MessageDigest digest = null;

				try {
					digest = MessageDigest.getInstance("SHA-512");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  digest.reset();
				  try {
					digest.update(usuario.getPassword().getBytes("utf8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  String toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
			
			if(u.getPassword().equals(toReturn)) {
				service.loadUserByUsername(u.getNombre());
				
				System.out.println("Logeado correctamente");
				System.out.println(u.getPassword());
				System.out.println(usuario.getPassword());
				
						String token = jwt.getJWTToken(u.getMail());

						u.setToken(token);
						u.setNull();
						tokenRepo.addToken(u.getMail(), token);
						tokenRepo.printTokens();
						return new ResponseEntity<Usuario>(u,HttpStatus.OK);
						
			}
			else {
				System.out.println("Contraseña incorrecta");
				return new ResponseEntity<Usuario>(HttpStatus.EXPECTATION_FAILED); //417 si contra incorrecta
			}
				
		}
		else {
			System.out.println("Usuario incorrecto");
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST); //400 si usr incorrecto
		}
		
		
	}
	
	
	//YA NO SE USA
	@GetMapping(value = "/returnImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getImage() throws IOException{
		InputStream in = getClass().getResourceAsStream("prueba.jpg");
		byte[] image = IOUtils.toByteArray(in);
		//comentarios
		return new ResponseEntity<byte[]>(image,HttpStatus.OK);
		
	}
	
	
	
	
	@PostMapping(value = "/acceptRequest")
	public ResponseEntity<Usuario> acceptRequest(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String mail = usuario.getMail();
		Usuario amigo = usuarioRepo.findByMail(mail);
		Usuario tu = usuarioRepo.findByMail(identificador);
		System.out.println(identificador);
		
		
		
		amigo.setAmigo(tu);
		tu.setAmigo(amigo);
		tu.deletePeticion(amigo);
		amigo.setnAmigos(amigo.getAmigo().size());
		tu.setnAmigos(tu.getAmigo().size());
		usuarioRepo.save(amigo);
		usuarioRepo.save(tu);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/denyRequest")
	public ResponseEntity<Usuario> denyRequest(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String mail = usuario.getMail();
		Usuario amigo = usuarioRepo.findByMail(mail);
		Usuario tu = usuarioRepo.findByMail(identificador);
		System.out.println(identificador);
		
		
		tu.deletePeticion(amigo);
		usuarioRepo.save(tu);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/listFriends")
	public ResponseEntity<List<Usuario>> listFriends(@RequestHeader String identificador){
				
		List<Usuario> respuesta = usuarioRepo.findByMail(identificador).getAmigo();
		return new ResponseEntity<List<Usuario>>(respuesta,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/listRequest")
	public ResponseEntity<List<Usuario>> listRequest(@RequestHeader String identificador){
		
		List<Usuario> respuesta = usuarioRepo.findByMail(identificador).getPeticion();
		return new ResponseEntity<List<Usuario>>(respuesta,HttpStatus.OK);
		
	}
	
	

	
	@PostMapping(value = "/sendRequest")
	public ResponseEntity<Usuario> sendRequest(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String nombreUsuario = usuario.getMail();
		Usuario destino = usuarioRepo.findByMail(nombreUsuario);
		Usuario tu = usuarioRepo.findByMail(identificador);
		System.out.println(identificador);
		
		
		if(nombreUsuario.equals(identificador)) {
			return new ResponseEntity<Usuario>(HttpStatus.CONFLICT); //409
		}
		
		if(destino != null) {
			if(tu.contiene(destino)) {
				tu.setAmigo(destino);
				destino.setAmigo(tu);
				tu.deletePeticion(destino);
				usuarioRepo.save(tu);
				usuarioRepo.save(destino);
				return new ResponseEntity<Usuario>(HttpStatus.ALREADY_REPORTED);
			}
			if(destino.setPeticion(tu)) {
				usuarioRepo.save(destino);
				return new ResponseEntity<Usuario>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Usuario>(HttpStatus.EXPECTATION_FAILED);
			}
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.METHOD_NOT_ALLOWED);
		}

		
		
	}
	
	

	@PostMapping(value = "/deleteFriend")
	public ResponseEntity<Usuario> deleteFriend(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String nombreUsuario = usuario.getMail();
		Usuario amigo = usuarioRepo.findByMail(nombreUsuario);
		Usuario tu = usuarioRepo.findByMail(identificador);
		System.out.println(identificador);
		
		
		
		amigo.deleteAmigo(tu);
		tu.deleteAmigo(amigo);
		amigo.setnAmigos(amigo.getAmigo().size());
		tu.setnAmigos(tu.getAmigo().size());
		usuarioRepo.save(amigo);
		usuarioRepo.save(tu);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/viewProfile")
	public ResponseEntity<Usuario> viewProfile(@RequestHeader String identificador){
		
		Usuario u = usuarioRepo.findByMail(identificador);
		u.setNull();
		return new ResponseEntity<Usuario>(u,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/returnImageProfile/{idFoto}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImageProfile(@PathVariable String idFoto) throws IOException{
		String fotoo = "/profilePictures/"+idFoto;
		InputStream in = getClass().getResourceAsStream(fotoo);
		if(in!=null) {
		System.out.println(fotoo);
		byte[] image = IOUtils.toByteArray(in);

		return new ResponseEntity<byte[]>(image,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	
	@GetMapping(value = "/changeImageProfile")
	public ResponseEntity<Integer> changeImageProfile(@RequestHeader String identificador,@RequestHeader String idFoto) throws IOException{
		
		
		
		
		if(game.cambiarFoto(identificador, idFoto)){
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
		else return new ResponseEntity<Integer>(HttpStatus.EXPECTATION_FAILED);
		
		

		
		
	}
	
	@PostMapping(value = "/changeNameProfile")
	public ResponseEntity<Usuario> changeName(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		
		Usuario u = usuarioRepo.findByMail(identificador);
		String nuevoNombre = usuario.getNombre();
		if(usuarioRepo.findByNombre(nuevoNombre)==null && nuevoNombre != null) {
			u.setNombre(nuevoNombre);
			usuarioRepo.save(u);
			return new ResponseEntity<Usuario>(HttpStatus.OK);
		}
		return new ResponseEntity<Usuario>(HttpStatus.EXPECTATION_FAILED);
		
	}
	
	@PostMapping(value = "/newGame")
	public ResponseEntity<Partida> newGame(@RequestBody Partida partida,@RequestHeader String identificador){

		if(partida.getNombre().length()>20) return new ResponseEntity<Partida>(HttpStatus.EXPECTATION_FAILED);		

		Partida p = game.crearPartida(identificador,partida);
		p.setNull();
		return new ResponseEntity<Partida>(p,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/acceptInvite")
	public ResponseEntity<Partida> acceptInvite(@RequestHeader String identificador,@RequestHeader int idPartida){
		
		if(game.addJugador(identificador,idPartida)) {
			
			Partida p = game.getPartida(idPartida);
			p.setNull();
			return new ResponseEntity<Partida>(p,HttpStatus.OK); 
		}
		return new ResponseEntity<Partida>(HttpStatus.EXPECTATION_FAILED);
		
	}
	
	@GetMapping(value = "/listGames")
	public ResponseEntity<List<Partida>> listGames (@RequestHeader String identificador){
		List<Partida> respuesta = game.getPartidasJugador(identificador);
		return new ResponseEntity<List<Partida>>(respuesta,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/listInvite")
	public ResponseEntity<List<Invitaciones>> listInvite (@RequestHeader String identificador){
		List<Invitaciones> respuesta = game.getInvitacionesJugador(identificador);
		return new ResponseEntity<List<Invitaciones>>(respuesta,HttpStatus.OK);
	}
	
	@GetMapping(value = "/listPlayers")
	public ResponseEntity<List<Usuario>> listPlayers(@RequestHeader int idPartida){
		
		List<Usuario> respuesta = game.listPlayers(idPartida);
		return new ResponseEntity<List<Usuario>>(respuesta,HttpStatus.OK);
	}
	
	@GetMapping(value = "/inviteGame")
	public ResponseEntity<String> inviteGame(@RequestHeader int idPartida,@RequestHeader String identificador,@RequestHeader String idInvitado){
				
		if(game.inviteGame(idInvitado,identificador, idPartida)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value = "/denyInvite")
	public ResponseEntity<Usuario> denyInvite(@RequestHeader int idPartida ,@RequestHeader String identificador){
		
		game.denyInvite(identificador, idPartida);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	

	@GetMapping(value = "/listFriendsGame")
	public ResponseEntity<List<Usuario>> listFriendsGame(@RequestHeader int idPartida, @RequestHeader String identificador){
		List<Usuario> respuesta = game.listPlayersGame(idPartida,identificador);
		return new ResponseEntity<List<Usuario>>(respuesta,HttpStatus.OK);
	}

	@GetMapping(value = "/startGame")
	public ResponseEntity<String> startGame(@RequestHeader int idPartida,@RequestHeader String identificador){
		int aux = game.startGame(identificador, idPartida);	
		if(0==aux) { //Correcto
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		else if(1==aux){ //Partida empezada
			return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE); //503
		}else if(3==aux){ //No se llega al minimo
			return new ResponseEntity<String>(HttpStatus.LOCKED); //423
		}else { //No eres el host
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping(value = "/addText")
	public ResponseEntity<String> addRespuesta(@RequestHeader int idPartida,@RequestHeader String autor,@RequestBody String contenido){
				
		if(game.addRespuesta(idPartida, autor,null,false,contenido)) {
			
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@PostMapping(value = "/addImage")
	public ResponseEntity<String> addRespuesta(@RequestHeader int idPartida,@RequestHeader String autor,@RequestBody MultipartFile contenido) throws IOException{
		//System.out.println("LLEGAMOS A AÑADIR RESPUESTA:"+ contenido.toString());		
		if(game.addRespuesta(idPartida, autor, contenido.getBytes(),true,null)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@PostMapping(value = "/addImage2")
	public ResponseEntity<String> addRespuesta2(@RequestHeader int idPartida,@RequestHeader String autor,@RequestBody String contenido) throws IOException{
		//System.out.println("LLEGAMOS A AÑADIR RESPUESTA:"+ contenido.toString());		
		String stringRecibido;

		// tokenize the data
		StringTokenizer st = new StringTokenizer(contenido,",");
		st.nextToken(); //El primero no importa
		String imageString = st.nextToken();
		byte[] imageByte;
		Base64.Decoder decoder = Base64.getDecoder();
		imageByte = decoder.decode(imageString);
		if(game.addRespuesta(idPartida, autor, imageByte,true,null)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	
	
	@GetMapping(value = "/returnImageResponse/{idFoto}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImageResponse(@PathVariable int idFoto){
		
		byte[]image = game.getImageResponse(idFoto);
		
		return new ResponseEntity<byte[]>(image,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/returnResponse")
	public ResponseEntity<Respuesta> getResponse(@RequestHeader String identificador, @RequestHeader int idPartida){
		
		Respuesta response = game.getResponse(identificador,idPartida);
		if(response == null) {
			return new ResponseEntity<Respuesta>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Respuesta>(response,HttpStatus.OK);	
	}
	
	
	@GetMapping(value = "/returnAllResponses")
	public ResponseEntity<Hilo[]> getAllResponses(@RequestHeader int idPartida){
		
		Hilo[] respuesta = game.getAllRespuestas(idPartida);
		
		return new ResponseEntity<Hilo[]>(respuesta,HttpStatus.OK);	
	}
	

	@GetMapping(value = "/votarGracioso")
	public ResponseEntity<String> votarGracioso(@RequestHeader int idPartida,@RequestHeader String identificador,@RequestHeader String votado){
		if(game.votarGracioso(idPartida,identificador,votado)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value = "/votarListo")
	public ResponseEntity<String> votarListo(@RequestHeader int idPartida,@RequestHeader String identificador,@RequestHeader String votado){
		if(game.votarListo(idPartida,identificador,votado)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value = "/votarDibujo")
	public ResponseEntity<String> votarDibujo(@RequestHeader int idPartida,@RequestHeader String identificador,@RequestHeader String votado){
		if(game.votarDibujo(idPartida,identificador,votado)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value = "/puntosJugador")
	public ResponseEntity<List<Integer>> puntosJugador(@RequestHeader int idPartida,@RequestHeader String identificador){
		List<Integer> p = game.puntosJugador(idPartida,identificador);
		if(p!=null) {
			return new ResponseEntity<List<Integer>>(p,HttpStatus.OK);
		}
		return new ResponseEntity<List<Integer>>(HttpStatus.EXPECTATION_FAILED);
	}
	
	@GetMapping(value = "/puntosPartida")
	public ResponseEntity<List<Puntos>> puntosPartida(@RequestHeader int idPartida,@RequestHeader String identificador){
		List<Puntos> p = game.puntosPartida(idPartida,identificador);
		if(p!=null) {
			return new ResponseEntity<List<Puntos>>(p,HttpStatus.OK);
		}
		return new ResponseEntity<List<Puntos>>(HttpStatus.EXPECTATION_FAILED);
	}
	
	@GetMapping(value = "/resetVotos")
	public ResponseEntity<String> resetVotos(@RequestHeader int idPartida){
		game.resetVotos(idPartida);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/*
	@GetMapping(value = "/sendNotification")
	public ResponseEntity<String> sendNotification(){
		String TOPIC = "si";
		
		
		String inigo = "cWaH0QF9TwigLV1l5WWfeT:APA91bE7YVL3mMFbahY5F_6LAuepybLFPiHuYlq_19qqtPR5Xl9M4gOWnK5C0B82UUY5hAHo9XfwYFFaVBFYpInn8ri3qUUhYlhmr_ibsOZmtqzsv2rgZ15Ai11ad9h-i2eVgao8ahaF";
		String hector = "cJa3fiHST5estCfnZFCMRq:APA91bEuJ5HECcHo6pGq-2_lYHCB7ttUvQDchz3otjwEJEhfV2hxtYOGdOD9YNTT8ziU5Uga47OuY2UhiKdm09UW7kQAgFdvayHtmQVuUfAWUNilYPXHsLD1oRiLId88X4P8sgKAZ6Y_";
		JSONObject body = new JSONObject();
		//body.put("to", "/topics/" + TOPIC);
		body.put("to",inigo);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", "JSA Notification");
		notification.put("body", "Happy Message!");
		
		
		/*
		JSONObject data = new JSONObject();
		data.put("Key-1", "JSA Data 1");
		data.put("Key-2", "JSA Data 2");
		
	

		body.put("notification", notification);
		//body.put("data", data);
		
		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = notificationService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();
			
			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	
		
	}*/

	 @GetMapping("/token")
	    public String sendPnsToDevice() {
		 	Nota nota = new Nota();
		 	nota.setBody("CUERPO");
		 	String inigo = "cWaH0QF9TwigLV1l5WWfeT:APA91bE7YVL3mMFbahY5F_6LAuepybLFPiHuYlq_19qqtPR5Xl9M4gOWnK5C0B82UUY5hAHo9XfwYFFaVBFYpInn8ri3qUUhYlhmr_ibsOZmtqzsv2rgZ15Ai11ad9h-i2eVgao8ahaF";
		 	nota.setTarget(inigo);
		 	nota.setTitle("TITULO");
	        return notificationService.sendPnsToDevice(nota);
	    }

	
	 @GetMapping("/listPictures")
	 public ResponseEntity<List<Foto>> listPictures(){	
		 List<Foto> respuesta = game.listarFotos();
		 return new ResponseEntity<List<Foto>>(respuesta,HttpStatus.OK);
	 }
	 
	 @GetMapping("/listUnlockedPictures")
	 public ResponseEntity<List<Foto>> listUnlockedPictures(@RequestHeader String identificador){	 
		 List<Foto> respuesta = game.listarFotosCompradas(identificador);
		 return new ResponseEntity<List<Foto>>(respuesta,HttpStatus.OK);
	 }
	 
	 @GetMapping("/listLockedPictures")
	 public ResponseEntity<List<Foto>> listLockedPictures(@RequestHeader String identificador){	 
		 List<Foto> respuesta = game.listarFotosBloqueadas(identificador);
		 return new ResponseEntity<List<Foto>>(respuesta,HttpStatus.OK);
	 }
	 
	 @GetMapping("/unlockPicture")
	 public ResponseEntity<String> unlockPicture(@RequestHeader String identificador, @RequestHeader String idFoto){
		 if(game.comprarFoto(identificador,idFoto)) {
			 return new ResponseEntity<String>(HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		 }
	 }
	 
	 
	 @GetMapping("/setMonedas")
	 public ResponseEntity<String> unlockPicture(@RequestHeader String identificador,@RequestHeader int monedas){
		 Usuario u = usuarioRepo.findByMail(identificador);
		 u.setMonedas(monedas);
		 usuarioRepo.save(u);
		 return new ResponseEntity<String>(HttpStatus.OK);
	 }
	
}