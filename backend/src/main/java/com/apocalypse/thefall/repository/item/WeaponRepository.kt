package com.apocalypse.thefall.repository.item

import com.apocalypse.thefall.entity.item.Weapon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WeaponRepository : JpaRepository<Weapon, Long> {
    @Query(
        """
        select distinct w
        from Weapon w
        left join fetch w.modes
        where w.id in :ids
    """
    )
    fun prefetchModesByIds(ids: Set<Long>): List<Weapon>

    @Query(
        """
        select distinct w
        from Weapon w
        left join fetch w.compatibleAmmo
        where w.id in :ids
    """
    )
    fun prefetchCompatibleAmmoByIds(ids: Set<Long>): List<Weapon>
}