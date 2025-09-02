package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "perk_instance",
    uniqueConstraints = [UniqueConstraint(columnNames = ["character_id", "perk_id"])]
)
open class PerkInstance : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    open var character: Character? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perk_id", nullable = false)
    open var perk: Perk? = null

    @Column(name = "value", nullable = false)
    open var value: Int = 0

    @Column(name = "is_active", nullable = false)
    open var active: Boolean = false
}
