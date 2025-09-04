package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable

@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "perk")
open class Perk : BaseNamedEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    open var code: PerkEnum? = null
}
