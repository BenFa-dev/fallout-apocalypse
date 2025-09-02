package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.ConditionEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import jakarta.persistence.*

@Entity
@Table(name = "condition")
open class Condition : BaseNamedEntity() {
    @Column(name = "code", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    open var code: ConditionEnum? = null
}