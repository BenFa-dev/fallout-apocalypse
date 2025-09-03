package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Condition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConditionRepository : JpaRepository<Condition, Long> {
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Condition>
}
