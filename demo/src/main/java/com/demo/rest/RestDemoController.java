package com.demo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.demo.controller.AuthController;
import com.demo.model.Amigo;
import com.demo.model.Usuario;
import com.demo.repository.AmigoRepo;
import com.demo.repository.TokenRepo;
import com.demo.repository.UsuarioRepo;
import com.demo.service.UserService;



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
	private AmigoRepo amigoRepo;
	
	
	@Autowired
	private UserService service;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping(value = "/all")
	public List<Usuario> listar(){
		return usuarioRepo.findAll();
	}
	
	
	

	
	
	@PostMapping(value = "/register")
	public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
		
		Usuario u = new Usuario();
		String nombreUsuario = usuario.getNombre();
		String mail = usuario.getMail();
		System.out.println(mail);
		if(usuarioRepo.findByMail(mail)==null) {
			u.setNombre(nombreUsuario);
			u.setMail(mail);
			u.setPassword(encoder.encode(usuario.getPassword()));
			u.setRole("USER");
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
	
			
			
			if(usuarioRepo.findByNombre(usuario.getNombre())!=null){
			
			
			if(BCrypt.checkpw(usuario.getPassword(),usuarioRepo.findByNombre(usuario.getNombre()).getPassword())) {
				
				service.loadUserByUsername(usuario.getNombre());
				
				System.out.println("Logeado correctamente");
				
						String token = jwt.getJWTToken(usuario.getNombre());

						usuario.setToken(token);
						
						tokenRepo.addToken(usuario.getNombre(), token);
						tokenRepo.printTokens();
						System.out.println("----------------");
						return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);
						
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
	
	
	
	
	@PostMapping(value = "/acceptFriend")
	public ResponseEntity<Usuario> acceptFriend(@RequestBody Usuario usuario,@RequestHeader String identificador){
		
		String nombreUsuario = usuario.getNombre();
		Usuario u = usuarioRepo.findByNombre(nombreUsuario);
		
		String mail = u.getMail();
		System.out.println(identificador);
		Amigo amigo1 = new Amigo();
		amigo1.setMailAmigo(usuarioRepo.findByNombre(identificador).getMail());
		amigo1.setMailUsuario(u.getMail());
		
		amigoRepo.save(amigo1);
		
		
		Amigo amigo2 = new Amigo();
		amigo2.setMailAmigo(u.getMail());
		amigo2.setMailUsuario(usuarioRepo.findByNombre(identificador).getMail());
		
	
		amigoRepo.save(amigo2);
		
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/listFriends")
	public ResponseEntity<List<String>> listFriends(@RequestHeader String identificador){
		
		
		List<String> listaAmigos = new ArrayList<>();
		
		return new ResponseEntity<List<String>>(listaAmigos,HttpStatus.OK);
	}
	
}
