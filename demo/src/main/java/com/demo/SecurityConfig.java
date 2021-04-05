package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.security.JWTAuthorizationFilter;
import com.demo.service.UserService;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserService userDetailsService;
	
	

	

	

	    @Override
	public void configure(HttpSecurity http) throws Exception {
	       http.cors().and().csrf().disable()
	       .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
	        .authorizeRequests()
	        .antMatchers("/").permitAll()
	        .antMatchers("v2/api-docs").permitAll()
	        .antMatchers("swagger-ui.html").permitAll()
		   .antMatchers(HttpMethod.POST, "/api/prueba").permitAll()
	       .antMatchers(HttpMethod.POST, "/api/login").permitAll()
	       .antMatchers(HttpMethod.POST, "/api/register").permitAll()
	       .antMatchers(HttpMethod.GET, "/api/returnImageProfile/*").permitAll()
			.anyRequest().authenticated();
	       
	        
	        
	}
	    
	    
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v2/api-docs",
	                                   "/configuration/ui",
	                                   "/swagger-resources/**",
	                                   "/configuration/security",
	                                   "/swagger-ui.html",
	                                   "/webjars/**");
	    }
	    
	    

	
}
