package com.apocalypse.thefall.repository

import com.apocalypse.thefall.entity.GameMap
import com.apocalypse.thefall.entity.Tile
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TileRepository : JpaRepository<Tile, Long> {

    fun findByMapAndXAndY(map: GameMap, x: Int, y: Int): Tile?

    @EntityGraph(attributePaths = ["terrainConfiguration"])
    fun findAllByMapId(mapId: Long): List<Tile>
}
