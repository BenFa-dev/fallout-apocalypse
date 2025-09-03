package com.apocalypse.thefall.repository

import com.apocalypse.thefall.entity.GameMap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MapRepository : JpaRepository<GameMap, Long> {
    fun findFirstByOrderByIdAsc(): GameMap?
}
