package com.apocalypse.thefall.entity.character

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class CharacterCurrentStats(

    @Column(name = "action_points")
    var actionPoints: Int = 0,

    @Column(name = "hit_points")
    var hitPoints: Int = 0,

    @Column(name = "level")
    var level: Int = 0
)
