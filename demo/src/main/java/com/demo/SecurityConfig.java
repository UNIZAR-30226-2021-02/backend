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

import com.demo.service.UserService;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll();
		http.csrf().disable();
		
	}
	*/

	    @Override
	public void configure(HttpSecurity http) throws Exception {
	       http.csrf().disable().authorizeRequests()
	        .antMatchers("/").permitAll()
	        .antMatchers("v2/api-docs").permitAll()
	        .antMatchers("swagger-ui.html").permitAll()
	        .antMatchers(HttpMethod.GET,"/api/all").permitAll()
	        .antMatchers(HttpMethod.POST, "/api/save").permitAll()
	        .antMatchers(HttpMethod.POST,"/newuser/*").permitAll()
	        .antMatchers(HttpMethod.GET,"/master/*").permitAll()
	        .antMatchers(HttpMethod.POST,"/api/*").permitAll()
	         .antMatchers(HttpMethod.GET,"/exploreCourse").permitAll()
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
