package com.demo.controller;



	
	import java.util.Date;
	import java.util.List;
	import java.util.stream.Collectors;

	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.authority.AuthorityUtils;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Usuario;


	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.SignatureAlgorithm;

	@RestController
	public class AuthController {

		

		public String getJWTToken(String username) {
			String secretKey = "mySecretKey";
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("USER");
			
			String token = Jwts
					.builder()
					.setId("softtekJWT")
					.setSubject(username)
					.claim("authorities",
							grantedAuthorities.stream()
									.map(GrantedAuthority::getAuthority)
									.collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 604800000))
					.signWith(SignatureAlgorithm.HS512,
							secretKey.getBytes()).compact();

			return "Bearer "+ token;
		}
	
	
	
}
