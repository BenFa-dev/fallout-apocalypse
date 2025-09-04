package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.ConditionEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable

@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "condition")
open class Condition : BaseNamedEntity() {
    @Column(name = "code", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    open var code: ConditionEnum? = null
}