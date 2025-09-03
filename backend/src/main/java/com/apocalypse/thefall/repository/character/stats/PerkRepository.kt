package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Perk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PerkRepository : JpaRepository<Perk, Long> {
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Perk>
}
