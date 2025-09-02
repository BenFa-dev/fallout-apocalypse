package com.apocalypse.thefall.entity.instance

import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.entity.inventory.Inventory
import com.apocalypse.thefall.entity.item.Item
import jakarta.persistence.*

@Entity
@Table(name = "item_instance")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
open class ItemInstance : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    open var item: Item? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    open var inventory: Inventory? = null
}
