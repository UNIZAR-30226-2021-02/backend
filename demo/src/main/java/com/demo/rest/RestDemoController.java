package com.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Persona;
import com.demo.repository.Repo;

@RestController
@RequestMapping(value = "/api")
public class RestDemoController {

	
	@Autowired
	private Repo repo;
	
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
}
