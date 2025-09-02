package com.apocalypse.thefall.service.inventory.handler

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.enums.EquippedSlot

interface ItemHandler<T : Item, I : ItemInstance> {
    fun createInstance(item: T): I
    fun validateRequirements(character: Character, item: T)
    fun equip(character: Character, itemInstance: I, slot: EquippedSlot)
    fun unequip(character: Character, itemInstance: I)
}
