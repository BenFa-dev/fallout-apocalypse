package com.apocalypse.thefall.dto.item.instance

import com.apocalypse.thefall.dto.item.AmmoDto
import com.apocalypse.thefall.dto.item.ItemDto
import com.apocalypse.thefall.dto.item.WeaponModeDto
import com.apocalypse.thefall.entity.item.enums.EquippedSlot

@JvmRecord
data class WeaponInstanceDto(
    override val id: Long?,
    val currentAmmoType: AmmoDto?,
    val currentWeaponMode: WeaponModeDto?,
    val currentAmmoQuantity: Int?,
    val equippedSlot: EquippedSlot?,
    override val item: ItemDto?
) : ItemInstanceDto 