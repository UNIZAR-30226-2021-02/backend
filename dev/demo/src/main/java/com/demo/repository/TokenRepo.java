package com.demo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TokenRepo {

	
	
	private Map<String,String> tokens = new HashMap<String,String>();
	
	
	
	
	public boolean findUser(String username) {
		return tokens.containsKey(username);
		
	}
	
	public void addToken(String username, String token) {
		tokens.put(username, token);
	}
	
	public String findToken(String username) {
		
		if(tokens.containsKey(username)) {
			return tokens.get(username);
		}
		else {
			return null;
		}
	}
	
	public void  printTokens() {
		for(Map.Entry<String,String> entry: tokens.entrySet()) {
			
			
			System.out.println(entry.getKey()+"  :  "+entry.getValue());
		}
	}
	
}
