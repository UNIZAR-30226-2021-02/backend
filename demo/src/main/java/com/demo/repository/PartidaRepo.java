package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Partida;
import com.demo.model.Usuario;

public interface PartidaRepo extends JpaRepository<Partida,Integer>{

	Usuario findByNombre(String nombre);

	Usuario findByMail(String mail);
	
	/*
	@Query(value = "SELECT Usuario.nombre FROM Amigos INNER JOIN Usuario ON Amigos.mail_amigo = mail  WHERE Amigos.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listFriends(@Param("mailUsuario") String nombreUsuario);
	
	
	@Query(value = "SELECT Usuario.nombre FROM Peticiones INNER JOIN Usuario ON Peticiones.mail_pedido = mail  WHERE Peticiones.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listRequest(@Param("mailUsuario") String nombreUsuario);
	*/
	
	
}
