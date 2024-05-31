package com.game.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {
    
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public BroadcastService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;

    }

    public void broadcastMessage(String message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }


}
