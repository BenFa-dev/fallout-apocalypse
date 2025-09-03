package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Special
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpecialRepository : JpaRepository<Special, Long> {
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Special>
}
