package com.demo.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.fcm.Note;
import com.demo.model.Nota;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@Service
public class NotificationService {

	/*
	private static final String FIREBASE_SERVER_KEY = "	AAAAnUFIUWs:APA91bExLCwA0kbRFzxrtmcv-3ZZ9dRRjQtLOcOR53S_EzBtY1iOa5jeWNoXbgTBUAo4VlgJVxTfBJVJXqDMrM77JERHgpwVp2EN05u8_YCW_fF3ZpnrDq1Cz0CDLAhVUub1HzVaIvtg";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	public CompletableFuture send(HttpEntity<String> message) {

		RestTemplate restTemplate = new RestTemplate();

		/**
		https://fcm.googleapis.com/fcm/send
		Content-Type:application/json
		Authorization:key=FIREBASE_SERVER_KEY

		ArrayList interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, message, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
	
	public void sendMessageToToken(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
       
    
	*/
	
	  public String sendPnsToDevice(Nota notificationRequestDto) {
	        Message message = Message.builder()
	                .setToken(notificationRequestDto.getTarget())
	                
	                .putData("content", notificationRequestDto.getTitle())
	                .putData("body", notificationRequestDto.getBody())
	                .build();

	        String response = null;
	        try {
	            response = FirebaseMessaging.getInstance().send(message);
	        } catch (FirebaseMessagingException e) {
	           System.out.println(e);
	        }

	        return response;
	    }
	
	
	
}
