package com.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Foto;

	

public interface FotoRepo extends JpaRepository<Foto,String>{
		
		
		Foto findByIdFoto(String idFoto);
		
		
		
		
}

