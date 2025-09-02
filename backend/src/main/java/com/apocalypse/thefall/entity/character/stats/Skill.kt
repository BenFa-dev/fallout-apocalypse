package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import com.apocalypse.thefall.service.character.rules.FormulaEntity
import jakarta.persistence.*

@Entity
@Table(name = "skill")
open class Skill : BaseNamedEntity(), FormulaEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    override lateinit var code: SkillEnum

    @Column(name = "formula")
    override lateinit var formula: String
}
