package com.apocalypse.thefall.service.inventory.factory

import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.item.Item

interface ItemInstanceFactory {
    fun createInstance(item: Item): ItemInstance?
}