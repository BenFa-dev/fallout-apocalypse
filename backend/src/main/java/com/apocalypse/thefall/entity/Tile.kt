package com.apocalypse.thefall.entity

import jakarta.persistence.*

@Entity
@Table(name = "tile")
open class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id")
    open var map: GameMap? = null

    @Column(nullable = false)
    open var x: Int = 0

    @Column(nullable = false)
    open var y: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terrain_configuration_id", nullable = false)
    open var terrainConfiguration: TerrainConfiguration? = null

    open val movementCost: Int? get() = terrainConfiguration?.movementCost

    open val isWalkable: Boolean? get() = terrainConfiguration?.walkable

    companion object {
        fun unknownAt(x: Int, y: Int, map: GameMap): Tile {
            val terrain = TerrainConfiguration().apply {
                name = "unknown"
                walkable = false
                movementCost = 999
                descriptions = mutableMapOf()
                path = ""
            }
            return Tile().apply {
                this.x = x
                this.y = y
                this.map = map
                this.terrainConfiguration = terrain
            }
        }
    }
}
