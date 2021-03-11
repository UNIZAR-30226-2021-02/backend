package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.demo.model.Persona;
import com.demo.repository.PersonaRepo;



@Controller
public class AppController {

	
	@Autowired
	private PersonaRepo r;
	
	@GetMapping("/hola")
	public String hola(){
		Persona p = new Persona();
		p.setId(6);
		p.setNombre("antonio");
		r.save(p);
			
		return "index";
	}
	
		
		
	
	
	
}
