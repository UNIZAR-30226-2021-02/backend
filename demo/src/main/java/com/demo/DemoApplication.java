package com.demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




 



@SpringBootApplication
public class DemoApplication {

	public static final int  MAX_JUGADORES = 5;
	
	public static void main(String[] args){
		
		
		SpringApplication.run(DemoApplication.class, args);
	}

	
	
}
