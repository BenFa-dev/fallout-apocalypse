package com.apocalypse.thefall.service

import com.apocalypse.thefall.event.CharacterMovementEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
open class EventService(
    private val kafkaTemplate: KafkaTemplate<String, CharacterMovementEvent>
) {

    fun publishMovementEvent(event: CharacterMovementEvent) {
        kafkaTemplate.send("player-movement", event)
    }
}
