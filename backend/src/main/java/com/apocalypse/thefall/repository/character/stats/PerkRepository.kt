package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Perk
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.QueryHints

interface PerkRepository : JpaRepository<Perk, Long> {
    @QueryHints(QueryHint(name = "org.hibernate.cacheable", value = "true"))
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Perk>
}
