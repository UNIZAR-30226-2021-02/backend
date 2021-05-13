package com.demo.fcm;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FCMInitializer {
	
	
	
    private String firebaseConfigPath = "serviceAccountKey.json";

    
    @PostConstruct
    public void initialize() {
    	
        try {
        	System.out.println("El path es "+ firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
            	
                FirebaseApp.initializeApp(options);
                
            }
        } catch (IOException e) {
            
    }

}
}