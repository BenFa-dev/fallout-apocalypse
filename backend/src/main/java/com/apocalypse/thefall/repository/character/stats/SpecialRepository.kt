package com.apocalypse.thefall.repository.character.stats

import com.apocalypse.thefall.entity.character.stats.Special
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.QueryHints

interface SpecialRepository : JpaRepository<Special, Long> {
    @QueryHints(QueryHint(name = "org.hibernate.cacheable", value = "true"))
    fun findAllByVisibleTrueOrderByDisplayOrderAsc(): List<Special>
}
