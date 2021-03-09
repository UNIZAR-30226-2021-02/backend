package com.demo.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.model.Usuario;
import com.demo.repository.UsuarioRepo;


@Service
public class UserService implements UserDetailsService{

	
	
	@Autowired
	private UsuarioRepo usuario;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario u =  usuario.findByNombre(username);
		
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		roles.add(new SimpleGrantedAuthority("USER"));
		
		UserDetails userDetails =  new User(u.getNombre(),u.getPassword(),roles);
		
		return  userDetails;
	}




}



