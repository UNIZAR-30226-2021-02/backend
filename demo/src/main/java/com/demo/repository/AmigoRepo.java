package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Amigo;


public interface AmigoRepo extends JpaRepository<Amigo,Integer>{

	
}
