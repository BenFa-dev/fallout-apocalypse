package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.service.character.rules.CalculatedInstance
import jakarta.persistence.*

@Entity
@Table(
    name = "skill_instance",
    uniqueConstraints = [UniqueConstraint(columnNames = ["character_id", "skill_id"])]
)
open class SkillInstance : BaseEntity(), CalculatedInstance<SkillEnum> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    open var character: Character? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    open var skill: Skill? = null

    @Column(name = "value", nullable = false)
    override var value: Int = 0

    @Transient
    override var calculatedValue: Int? = null

    @Column(name = "is_tagged", nullable = false)
    open var tagged: Boolean = false

    override val code: SkillEnum get() = skill!!.code
}
