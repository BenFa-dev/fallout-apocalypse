package com.apocalypse.thefall.service.initializer

import com.apocalypse.thefall.service.GenerateMapService
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GameInitializer(
    private val generateMapService: GenerateMapService,
    private val characterStatsInitService: CharacterStatsInitService
) {
    private val log = LoggerFactory.getLogger(GameInitializer::class.java)

    @PostConstruct
    fun init() {
        log.info("🚀  Initializing game world...")

        // Initializes a default map if no existing one is found
        generateMapService.getOrCreateMap()
        log.info("🗺️  Map initialized")

        // Preload immutable world data
        characterStatsInitService.preloadAll()

        log.info("✅  Game world initialization completed")
    }
}
