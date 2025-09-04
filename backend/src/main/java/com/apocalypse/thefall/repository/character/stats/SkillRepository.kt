package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Skill
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.QueryHints

interface SkillRepository : JpaRepository<Skill, Long> {
    @QueryHints(QueryHint(name = "org.hibernate.cacheable", value = "true"))
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Skill>
}
