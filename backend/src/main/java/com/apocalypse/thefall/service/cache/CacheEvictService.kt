package com.apocalypse.thefall.service.cache

import org.hibernate.SessionFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CacheEvictService(
    private val sessionFactory: SessionFactory
) {
    @Transactional
    open fun evictReferenceDataCaches() {
        val cache = sessionFactory.cache
        // Entités lookup
        cache.evictEntityData(com.apocalypse.thefall.entity.character.stats.Special::class.java)
        cache.evictEntityData(com.apocalypse.thefall.entity.item.DamageType::class.java)
        cache.evictEntityData(com.apocalypse.thefall.entity.item.WeaponMode::class.java)
        cache.evictEntityData(com.apocalypse.thefall.entity.character.stats.Perk::class.java)
        cache.evictEntityData(com.apocalypse.thefall.entity.character.stats.Skill::class.java)
        cache.evictEntityData(com.apocalypse.thefall.entity.character.stats.DerivedStat::class.java)
        cache.evictEntityData(com.apocalypse.thefall.entity.character.stats.Condition::class.java)

        // Collections
        cache.evictCollectionData("com.apocalypse.thefall.entity.item.Weapon.modes")
        cache.evictCollectionData("com.apocalypse.thefall.entity.item.Weapon.compatibleAmmo")
        cache.evictCollectionData("com.apocalypse.thefall.entity.item.Armor.damages")

        cache.evictQueryRegions()
    }
}
