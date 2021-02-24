package com.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.model.Usuario;
import com.demo.repository.UsuarioRepo;

@SpringBootTest
class DemoApplicationTests {

	
	@Autowired
	private UsuarioRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Test
	void crearUsuarioTest() {
		
		Usuario u = new Usuario();
		u.setId(5);
		u.setNombre("antonio");
		u.setPassword(encoder.encode("1234"));
		
		Usuario u2 = repo.save(u);
		
		assertTrue(u2.getPassword().equalsIgnoreCase(u.getPassword()));
		
	}

}
