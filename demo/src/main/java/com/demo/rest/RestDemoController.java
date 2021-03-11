package com.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.controller.JWTController;
import com.demo.controller.authController;
import com.demo.model.Persona;
import com.demo.model.Usuario;
import com.demo.repository.PersonaRepo;
import com.demo.repository.TokenRepo;
import com.demo.repository.UsuarioRepo;
import com.demo.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class RestDemoController {

	
	@Autowired
	private authController jwt;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	@Autowired
	private PersonaRepo repo;
	
	@Autowired 
	private UsuarioRepo usuarioRepo;
	
	
	@Autowired
	private UserService service;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping(value = "/all")
	public List<Persona> listar(){
		return repo.findAll();
	}
	
	@GetMapping(value = "/find/{id}")
	public Persona find(@PathVariable Integer id) {
		return repo.getOne(id);
	}
	
	@PostMapping(value = "/save")
	public void save(@RequestBody Persona persona){
		repo.save(persona);
		
	}
	
	@PutMapping(value = "/update")
	public void modify(@RequestBody Persona persona){
		repo.save(persona);
		
	}
	
	@DeleteMapping(value = "delete/{id}")
	public void delete(@PathVariable("id") Integer id){
		
		repo.deleteById(id);
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
		
		Usuario u = new Usuario();
		String nombreUsuario = usuario.getNombre();
		if(usuarioRepo.findByNombre(nombreUsuario)==null) {
			u.setNombre(nombreUsuario);
			u.setPassword(encoder.encode(usuario.getPassword()));
			u.setRole("USER");
			usuarioRepo.save(u);
			System.out.println("Como el usuario no existe, se crea");
			return new ResponseEntity<Usuario>(HttpStatus.CREATED);
		
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
			}
				
		}
		else {
			System.out.println("Usuario incorrecto");
		}
		
		return null;
	}
	
}
