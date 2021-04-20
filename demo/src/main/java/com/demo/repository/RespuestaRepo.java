package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Respuesta;
import com.demo.model.Usuario;

public interface RespuestaRepo extends JpaRepository<Respuesta,Integer>{
	/*
	Usuario findByNombre(String nombre);

	Usuario findByMail(String mail);
	*/
	
	Respuesta findById(int id);
	/*
	@Query(value = "SELECT Usuario.nombre FROM Amigos INNER JOIN Usuario ON Amigos.mail_amigo = mail  WHERE Amigos.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listFriends(@Param("mailUsuario") String nombreUsuario);
	
	
	@Query(value = "SELECT Usuario.nombre FROM Peticiones INNER JOIN Usuario ON Peticiones.mail_pedido = mail  WHERE Peticiones.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listRequest(@Param("mailUsuario") String nombreUsuario);
	*/
	
	
}
