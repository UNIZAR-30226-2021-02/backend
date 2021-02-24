package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario,Integer>{

	Usuario findByNombre(String nombre);
	
}
