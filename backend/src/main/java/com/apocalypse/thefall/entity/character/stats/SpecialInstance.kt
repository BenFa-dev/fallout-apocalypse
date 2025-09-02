package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "special_instance", uniqueConstraints = [UniqueConstraint(columnNames = ["character_id", "special_id"])])
open class SpecialInstance : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    open lateinit var character: Character

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_id", nullable = false)
    open lateinit var special: Special

    @Column(name = "value", nullable = false)
    open var value: Int = 0
}
