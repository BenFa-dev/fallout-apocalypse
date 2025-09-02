package com.apocalypse.thefall.service

import com.apocalypse.thefall.entity.GameMap
import com.apocalypse.thefall.entity.Tile
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.model.DiscoveryData
import com.apocalypse.thefall.model.TileWithState
import com.apocalypse.thefall.repository.MapRepository
import com.apocalypse.thefall.repository.TileRepository
import com.apocalypse.thefall.service.character.CharacterService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class MapService(
    private val characterService: CharacterService,
    private val discoveryService: CharacterTileDiscoveryService,
    private val mapRepository: MapRepository,
    private val tileRepository: TileRepository
) {

    @Transactional(readOnly = true)
    open fun getMap(mapId: Long): GameMap =
        mapRepository.findById(mapId).orElseThrow {
            GameException(
                "error.resource.notfound",
                HttpStatus.NOT_FOUND,
                "Map",
                "id",
                mapId.toString()
            )
        }

    /**
     * Loads the initial state of the map for the player:
     * - Marks the tiles already discovered
     * - Marks as visible those within the field of view
     * - Hides unknown tiles (not discovered nor visible)
     * - Updates newly discovered ones if necessary
     */
    @Transactional
    open fun getInitialTiles(userId: String, mapId: Long): List<TileWithState> {
        val data = updateDiscoveryTiles(userId, mapId)
        val range = 2

        return data.allTiles.map { tile ->
            val revealed = (tile.id in data.discoveredTileIds) || (tile in data.toDiscover)
            val visible = MapUtils.isInVision(tile, data.character.currentX, data.character.currentY, range)

            if (!revealed && !visible) {
                // tile completely unknown: return it as "hidden"
                TileWithState(Tile.unknownAt(tile.x, tile.y, tile.map!!), false, false)
            } else {
                TileWithState(tile, revealed, visible)
            }
        }
    }

    /**
     * Dynamically reveals new tiles visible after the player's movement.
     * Updates the set of newly discovered tiles.
     */
    @Transactional
    open fun discoverNewVisibleTiles(userId: String, mapId: Long): List<TileWithState> {
        val data = updateDiscoveryTiles(userId, mapId)
        val character = data.character
        val range = 2

        val currentlyVisible: List<Tile> = data.allTiles.filter {
            MapUtils.isInVision(it, character.currentX, character.currentY, range)
        }

        return currentlyVisible.map { tile ->
            val revealed = (tile.id in data.discoveredTileIds) || (tile in data.toDiscover)
            TileWithState(tile, revealed, true)
        }
    }

    private fun updateDiscoveryTiles(userId: String, mapId: Long): DiscoveryData {
        val character: Character = characterService.getSimpleCharacterByUserId(userId)
        val discoveredTileIds: Set<Long> = discoveryService.getDiscoveredTileIds(character.id!!)
        val range = 2

        val allTiles: List<Tile> = tileRepository.findAllByMapId(mapId)

        // Tiles currently visible but not previously discovered
        val toDiscover: List<Tile> = allTiles
            .filter { it.id !in discoveredTileIds }
            .filter { MapUtils.isInVision(it, character.currentX, character.currentY, range) }

        discoveryService.updateTilesDiscovery(character, toDiscover)

        return DiscoveryData(character, discoveredTileIds, allTiles, toDiscover)
    }
}
