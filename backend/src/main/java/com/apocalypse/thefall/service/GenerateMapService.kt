package com.apocalypse.thefall.service

import com.apocalypse.thefall.entity.GameMap
import com.apocalypse.thefall.entity.Tile
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.MapRepository
import com.apocalypse.thefall.repository.TerrainConfigurationRepository
import com.apocalypse.thefall.repository.TileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class GenerateMapService(
    private val mapRepository: MapRepository,
    private val tileRepository: TileRepository,
    private val terrainConfigurationRepository: TerrainConfigurationRepository,
    private val random: Random = Random()
) {

    @Transactional
    open fun getOrCreateMap(): GameMap = mapRepository.findFirstByOrderByIdAsc() ?: generateMap(15, 15)

    @Transactional
    open fun generateMap(width: Int, height: Int): GameMap {
        val terrainTypes = terrainConfigurationRepository.findAll()
        if (terrainTypes.isEmpty()) {
            throw GameException("error.game.configuration.missing", HttpStatus.INTERNAL_SERVER_ERROR, "terrain")
        }

        var map = GameMap().apply {
            name = "Wasteland"
            this.width = width
            this.height = height
        }
        map = mapRepository.save(map)

        // Generate tiles
        for (x in 0 until width) {
            for (y in 0 until height) {
                val tile = Tile().apply {
                    this.map = map
                    this.x = x
                    this.y = y
                    this.terrainConfiguration = terrainTypes[random.nextInt(terrainTypes.size)]
                }
                tileRepository.save(tile)
            }
        }

        return map
    }
}
