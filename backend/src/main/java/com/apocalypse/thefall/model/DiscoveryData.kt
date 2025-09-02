package com.apocalypse.thefall.model

import com.apocalypse.thefall.entity.Tile
import com.apocalypse.thefall.entity.character.Character

@JvmRecord
data class DiscoveryData(
    val character: Character,
    val discoveredTileIds: Set<Long>,
    val allTiles: List<Tile>,
    val toDiscover: List<Tile>
)
