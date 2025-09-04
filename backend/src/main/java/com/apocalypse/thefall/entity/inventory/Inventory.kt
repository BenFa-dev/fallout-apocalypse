package com.apocalypse.thefall.entity.inventory

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.entity.instance.ArmorInstance
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.instance.WeaponInstance
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "inventory")
open class Inventory : BaseEntity() {

    @OneToOne
    @JoinColumn(name = "character_id", nullable = false)
    open var character: Character? = null

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "inventory")
    open var items: MutableSet<ItemInstance> = mutableSetOf()

    fun getCurrentWeight(): Double = items.sumOf { it.item?.weight ?: 0.0 }

    /**
     * Get equipped slot items.
     */
    fun getEquippedItemsBySlot(): Map<EquippedSlot, ItemInstance> {
        val equipped = mutableMapOf<EquippedSlot, ItemInstance>()
        for (item in items) {
            when (item) {
                is ArmorInstance -> item.equippedSlot?.let { equipped.putIfAbsent(it, item) }
                is WeaponInstance -> item.equippedSlot?.let { equipped.putIfAbsent(it, item) }
            }
        }
        return equipped
    }
}
