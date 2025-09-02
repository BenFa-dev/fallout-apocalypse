package com.apocalypse.thefall.entity.character

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class CharacterCurrentStats(

    @Column(name = "action_points")
    open var actionPoints: Int = 0,

    @Column(name = "hit_points")
    open var hitPoints: Int = 0,

    @Column(name = "level")
    open var level: Int = 0
)
