package com.apocalypse.thefall.entity

import com.apocalypse.thefall.entity.character.Character
import jakarta.persistence.*

@Entity
@Table(name = "character_tile_discovery")
open class CharacterTileDiscovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    open var character: Character? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_id", nullable = false)
    open var tile: Tile? = null
}