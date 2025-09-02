package com.apocalypse.thefall.service;

import com.apocalypse.thefall.event.CharacterMovementEvent;
import com.apocalypse.thefall.event.GameEvent;
import com.apocalypse.thefall.event.GameEventType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaWebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "player-movement", groupId = "apocalypse-the-fall-game-group")
    public void listenPlayerMovement(CharacterMovementEvent event) {
        System.out.println("Envoi WebSocket sur /topic/movement : " + event);
        messagingTemplate.convertAndSend("/topic/movement", GameEvent.of(GameEventType.CHARACTER_ENTER));
    }
}
