package com.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Partida;
import com.demo.model.Usuario;

public interface PartidaRepo extends JpaRepository<Partida,Integer>{

	//Usuario findByNombre(String nombre);

	//Usuario findByMail(String mail);
	
	Partida findById(int id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Invitaciones VALUES (:mailUsuario,:idPartida,:mailInvitador)" , nativeQuery = true)
	public int inviteGame(@Param("mailUsuario") String nombreUsuario,@Param("mailInvitador") String nombreInvitador,@Param("idPartida") int idPartida);
	
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Invitaciones WHERE mail_usuario = :mailUsuario AND id_partida = :idPartida" , nativeQuery = true)
	public int deleteInvite(@Param("mailUsuario") String nombreUsuario,@Param("idPartida") int idPartida);
	
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Partida WHERE Partida.id_ = :idPartida " , nativeQuery = true)
	public int deletePartida(@Param("idPartida") int idPartida);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Hilo WHERE partida__id_ = :idPartida " , nativeQuery = true)
	public int deleteHilosPartida(@Param("idPartida") int idPartida);
	
	@Transactional
	@Modifying
	@Query( nativeQuery = true, value = "DELETE FROM Respuesta USING Hilo WHERE Hilo.id_=Respuesta.hilo__id_ AND  Hilo.partida__id_ = :idPartida"	)
	public int deleteRespuestasPartida(@Param("idPartida") int idPartida);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Jugadores WHERE partida = :idPartida " , nativeQuery = true)
	public int deleteJugadoresPartida(@Param("idPartida") int idPartida);
	
	
	/*
	@Query(value = "SELECT Usuario.nombre FROM Amigos INNER JOIN Usuario ON Amigos.mail_amigo = mail  WHERE Amigos.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listFriends(@Param("mailUsuario") String nombreUsuario);
	
	
	@Query(value = "SELECT Usuario.nombre FROM Peticiones INNER JOIN Usuario ON Peticiones.mail_pedido = mail  WHERE Peticiones.mail_usuario = :mailUsuario" , nativeQuery = true)
	
	List<String> listRequest(@Param("mailUsuario") String nombreUsuario);
	*/
	
	
}
