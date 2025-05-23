package com.game.gamezxne.rps.service;

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

    public void broadcastMessage(Object message, String destination) {
        messagingTemplate.convertAndSend("/topic/messages/"+destination, message);

    }


}
