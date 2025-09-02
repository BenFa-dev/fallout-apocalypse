package com.apocalypse.thefall.entity

import com.apocalypse.thefall.entity.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "map")
open class GameMap : BaseEntity() {

    @Column
    open var name: String? = null

    @Column(nullable = false)
    open var width: Int = 10

    @Column(nullable = false)
    open var height: Int = 10

    @OneToMany(mappedBy = "map", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var tiles: MutableSet<Tile> = mutableSetOf()
}
