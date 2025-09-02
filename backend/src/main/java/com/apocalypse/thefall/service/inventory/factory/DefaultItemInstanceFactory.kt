package com.apocalypse.thefall.service.inventory.factory

import com.apocalypse.thefall.entity.instance.AmmoInstance
import com.apocalypse.thefall.entity.instance.ArmorInstance
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.instance.WeaponInstance
import com.apocalypse.thefall.entity.item.Ammo
import com.apocalypse.thefall.entity.item.Armor
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.Weapon
import org.springframework.stereotype.Component

@Component
class DefaultItemInstanceFactory : ItemInstanceFactory {

    override fun createInstance(item: Item): ItemInstance {
        return when (item) {
            is Weapon -> WeaponInstance().apply {
                this.item = item
                this.currentAmmoQuantity = 0
            }

            is Armor -> ArmorInstance().apply {
                this.item = item
            }

            is Ammo -> AmmoInstance().apply {
                this.item = item
                this.quantity = 0
            }

            else -> throw IllegalArgumentException("Unsupported item type: ${item::class.simpleName}")
        }
    }
}
