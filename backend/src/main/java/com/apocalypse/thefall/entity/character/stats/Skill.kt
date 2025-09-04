package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import com.apocalypse.thefall.service.character.rules.FormulaEntity
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable

@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "skill")
open class Skill : BaseNamedEntity(), FormulaEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    override lateinit var code: SkillEnum

    @Column(name = "formula")
    override lateinit var formula: String
}
