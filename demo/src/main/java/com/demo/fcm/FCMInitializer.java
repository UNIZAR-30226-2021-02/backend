package com.demo.fcm;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FCMInitializer {
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
    @PostConstruct
    public void initialize() {
    	
        try {
        	System.out.println("Llego, mi fichero es "+ firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                        FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}