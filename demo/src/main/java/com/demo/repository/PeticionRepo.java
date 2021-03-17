package com.demo.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.Peticion;
import com.demo.model.Usuario;


public interface PeticionRepo extends JpaRepository<Peticion,Integer>{

	
	
	@Query(value = "SELECT Usuario.nombre FROM Peticion INNER JOIN Usuario ON peticion.mail_pedido = mail  WHERE peticion.mail_usuario = :mailUsuario" , nativeQuery = true)
	List<String> findRequestByMail(@Param("mailUsuario") String nombreUsuario);
}
