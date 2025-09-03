package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.DerivedStat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DerivedStatRepository : JpaRepository<DerivedStat, Long> {
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<DerivedStat>
}
