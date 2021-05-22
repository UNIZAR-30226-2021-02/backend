package com.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Hilo;
import com.demo.model.Invitaciones;
import com.demo.model.Partida;
import com.demo.model.Usuario;

public interface InvitacionesRepo extends JpaRepository<Invitaciones,Integer>{
	/*
	Usuario findByNombre(String nombre);

	Usuario findByMail(String mail);
	*/
	Invitaciones findById(int id);
	List<Invitaciones> findByPartida(Partida idPartida);
	Invitaciones findByPartidaAndInvitado(Partida idPartida,Usuario invitadoMail);
	List<Invitaciones> findByInvitado(Usuario invitadoMail);
	/*
	@Query(value = "SELECT Usuario.nombre FROM Amigos INNER JOIN Usuario ON Amigos.mail_amigo = mail  WHERE Amigos.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listFriends(@Param("mailUsuario") String nombreUsuario);
	
	
	@Query(value = "SELECT Usuario.nombre FROM Peticiones INNER JOIN Usuario ON Peticiones.mail_pedido = mail  WHERE Peticiones.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listRequest(@Param("mailUsuario") String nombreUsuario);
	*/
	
	
}
