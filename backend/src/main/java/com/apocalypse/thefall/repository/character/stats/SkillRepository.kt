package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Skill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillRepository : JpaRepository<Skill, Long> {
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Skill>
}
