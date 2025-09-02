package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.DerivedStatEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import com.apocalypse.thefall.service.character.rules.FormulaEntity
import jakarta.persistence.*

@Entity
@Table(name = "derived_stat")
open class DerivedStat : BaseNamedEntity(), FormulaEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    override lateinit var code: DerivedStatEnum

    @Column(name = "formula")
    override lateinit var formula: String
}
