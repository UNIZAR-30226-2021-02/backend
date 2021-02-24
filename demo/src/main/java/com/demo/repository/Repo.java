package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Persona;

public interface Repo extends JpaRepository<Persona,Integer>{

	
	
}
