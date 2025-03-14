package com.apocalypse.thefall.service;

import com.apocalypse.thefall.event.CharacterMovementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final KafkaTemplate<String, CharacterMovementEvent> kafkaTemplate;

    public void publishMovementEvent(CharacterMovementEvent event) {
        kafkaTemplate.send("player-movement", event);
    }
}