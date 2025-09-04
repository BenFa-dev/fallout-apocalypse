package com.apocalypse.thefall.repository

import com.apocalypse.thefall.entity.item.DamageType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.QueryHints

interface DamageTypeRepository : JpaRepository<DamageType, Long> {

    @QueryHints(
        value = [QueryHint(name = "org.hibernate.cacheable", value = "true")]
    )
    override fun findAll(): List<DamageType>
}