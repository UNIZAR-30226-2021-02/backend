package com.demo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.controller.AuthController;

import com.demo.model.Usuario;

import com.demo.repository.TokenRepo;
import com.demo.repository.UsuarioRepo;
import com.demo.service.UserService;


@CrossOrigin(origins = "http://localhost:8081",methods={RequestMethod.POST,RequestMethod.GET})
@RestController
@RequestMapping(value = "/api")
public class RestDemoController {

	
	@Autowired
	private AuthController jwt;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	
	@Autowired 
	private UsuarioRepo usuarioRepo;
	

	
	@Autowired
	private UserService service;


	
	@GetMapping(value = "/all")
	public List<Usuario> listar(){
		return usuarioRepo.findAll();
	}
	
	
	@PostMapping(value="/prueba")
	public void probar() {
		
	}

	
	@PostMapping(value = "/register")
	public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
		usuario.printUser();
		Usuario u = new Usuario();
		String nombreUsuario = usuario.getNombre();
		String mail = usuario.getMail();
		System.out.println(mail);
		if(usuarioRepo.findByMail(mail)==null&&usuarioRepo.findByNombre(nombreUsuario)==null) {
			u.setNombre(nombreUsuario);
			u.setMail(mail);
			u.setPassword(usuario.getPassword());
			u.setRole("USER");
			u.setnAmigos(0);
			u.setPuntos(new Integer(0),new Integer(0),new Integer(0),new Integer(0),new Integer(0));
			u.setFotPerf("foto0.png");
			usuarioRepo.save(u);
			System.out.println("Como el usuario no existe, se crea");
			
			String token = jwt.getJWTToken(nombreUsuario);
			u.setToken(token);
			return new ResponseEntity<Usuario>(u,HttpStatus.CREATED);
		
		}
		else {
			System.out.println("El usuario ya existe, por lo que no lo creamos");
			
			return new ResponseEntity<Usuario>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	

	
	@PostMapping(value = "/login")
	public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
	
			Usuario u = usuarioRepo.findByNombre(usuario.getNombre());
			
			if(u !=null){
			if(u.getPassword().equals(usuarioRepo.findByNombre(u.getNombre()).getPassword())) {
				
				service.loadUserByUsername(u.getNombre());
				
				System.out.println("Logeado correctamente");
				
						String token = jwt.getJWTToken(u.getNombre());

						u.setToken(token);
						u.setNull();
						tokenRepo.addToken(u.getNombre(), token);
						tokenRepo.printTokens();
						System.out.println("----------------");
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
	
	
	
	@GetMapping(value = "/returnImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getImage() throws IOException{
		InputStream in = getClass().getResourceAsStream("prueba.jpg");
		byte[] image = IOUtils.toByteArray(in);
		//comentarios
		return new ResponseEntity<byte[]>(image,HttpStatus.OK);
		
	}
	
	
	
	
	@PostMapping(value = "/acceptRequest")
	public ResponseEntity<Usuario> acceptRequest(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String nombreUsuario = usuario.getNombre();
		Usuario amigo = usuarioRepo.findByNombre(nombreUsuario);
		Usuario tu = usuarioRepo.findByNombre(identificador);
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
		
		String nombreUsuario = usuario.getNombre();
		Usuario amigo = usuarioRepo.findByNombre(nombreUsuario);
		Usuario tu = usuarioRepo.findByNombre(identificador);
		System.out.println(identificador);
		
		
		tu.deletePeticion(amigo);
		usuarioRepo.save(tu);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/listFriends")
	public ResponseEntity<List<Usuario>> listFriends(@RequestHeader String identificador){
				
		List<Usuario> respuesta = usuarioRepo.findByNombre(identificador).getAmigo();
		return new ResponseEntity<List<Usuario>>(respuesta,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping(value = "/listRequest")
	public ResponseEntity<List<Usuario>> listRequest(@RequestHeader String identificador){
		
		List<Usuario> respuesta = usuarioRepo.findByNombre(identificador).getPeticion();
		return new ResponseEntity<List<Usuario>>(respuesta,HttpStatus.OK);
		
	}
	
	

	
	@PostMapping(value = "/sendRequest")
	public ResponseEntity<Usuario> sendRequest(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String nombreUsuario = usuario.getNombre();
		Usuario destino = usuarioRepo.findByNombre(nombreUsuario);
		Usuario tu = usuarioRepo.findByNombre(identificador);
		System.out.println(identificador);
		
		
		
		if(destino.setPeticion(tu)) {
			usuarioRepo.save(destino);
			return new ResponseEntity<Usuario>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.EXPECTATION_FAILED);
		}
		

		
		
	}
	
	

	@PostMapping(value = "/deleteFriend")
	public ResponseEntity<Usuario> deleteFriend(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String nombreUsuario = usuario.getNombre();
		Usuario amigo = usuarioRepo.findByNombre(nombreUsuario);
		Usuario tu = usuarioRepo.findByNombre(identificador);
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
		
		Usuario u = usuarioRepo.findByNombre(identificador);
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
		
		
		
		Usuario u = usuarioRepo.findByNombre(identificador);
		System.out.println(identificador+"--"+idFoto);
		u.setFotPerf(idFoto);
		usuarioRepo.save(u);

		return new ResponseEntity<Integer>(HttpStatus.OK);
		
	}
	
	
	
}