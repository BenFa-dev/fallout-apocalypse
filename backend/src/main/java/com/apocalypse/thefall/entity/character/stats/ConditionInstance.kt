package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "condition_instance",
    uniqueConstraints = [UniqueConstraint(columnNames = ["character_id", "condition_id"])]
)
open class ConditionInstance : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    open var character: Character? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    open var condition: Condition? = null

    @Column(name = "value", nullable = false)
    open var value = false
}