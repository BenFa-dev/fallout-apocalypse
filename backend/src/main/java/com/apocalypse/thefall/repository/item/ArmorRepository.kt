package com.apocalypse.thefall.repository.item

import com.apocalypse.thefall.entity.item.Armor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArmorRepository : JpaRepository<Armor, Long> {
    @Query(
        """
        select distinct a
        from Armor a
        left join fetch a.damages d
        left join fetch d.damageType
        where a.id in :ids
    """
    )
    fun prefetchDamagesAndTypesByIds(ids: Set<Long>): List<Armor>
}