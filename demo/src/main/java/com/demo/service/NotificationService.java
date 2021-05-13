package com.demo.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.fcm.Note;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;



@Service
public class NotificationService {

	
	private static final String FIREBASE_SERVER_KEY = "AAAAnUFIUWs:APA91bF04hdPhtcNuKRE2foNNBqX1PPbx2DNVWhzYWiGBQBdg8F7ZL9KBtlXXynLdWMDLQxMp-3qpy7HZu3PR6nbyxPNwb-3utwbZffM2REajLpFdeDoMD-X819xI2JmVSw6ej0M3-_9";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	public CompletableFuture send(HttpEntity entity) {

		RestTemplate restTemplate = new RestTemplate();

		/**
		https://fcm.googleapis.com/fcm/send
		Content-Type:application/json
		Authorization:key=FIREBASE_SERVER_KEY*/

		ArrayList interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
	
	
}
