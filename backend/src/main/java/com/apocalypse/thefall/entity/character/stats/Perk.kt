package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import jakarta.persistence.*

@Entity
@Table(name = "perk")
open class Perk : BaseNamedEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    open var code: PerkEnum? = null
}
