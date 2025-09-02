package com.apocalypse.thefall.config

import com.apocalypse.thefall.service.GenerateMapService
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class GameInitializer(
    private val generateMapService: GenerateMapService
) {

    @PostConstruct
    fun init() {
        // Initializes a default map if no existing one is found
        generateMapService.getOrCreateMap()
    }
}
