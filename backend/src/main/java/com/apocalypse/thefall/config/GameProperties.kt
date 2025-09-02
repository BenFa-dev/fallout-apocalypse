package com.apocalypse.thefall.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "game")
open class GameProperties(
    var inventory: InventoryProperties = InventoryProperties()
) {
    data class InventoryProperties(
        var baseWeightCapacity: Int = 150,
        var weightPerStrength: Int = 10,
        var actionPoints: ActionPoints = ActionPoints()
    ) {
        data class ActionPoints(
            var equip: Int = 1,
            var unequip: Int = 1,
            var reload: Int = 2,
            var unload: Int = 1
        )
    }
}
