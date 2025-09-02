package com.apocalypse.thefall.service

import com.apocalypse.thefall.event.CharacterMovementEvent
import com.apocalypse.thefall.event.GameEvent
import com.apocalypse.thefall.event.GameEventType
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
open class KafkaWebSocketService(
    private val messagingTemplate: SimpMessagingTemplate
) {
    private val log = LoggerFactory.getLogger(KafkaWebSocketService::class.java)

    @KafkaListener(topics = ["player-movement"], groupId = "apocalypse-the-fall-game-group")
    open fun listenPlayerMovement(event: CharacterMovementEvent) {
        log.info("Envoi WebSocket sur /topic/movement : $event")
        messagingTemplate.convertAndSend(
            "/topic/movement",
            GameEvent.of(GameEventType.CHARACTER_ENTER)
        )
    }
}
