package com.apocalypse.thefall.mapper.item.instance

import com.apocalypse.thefall.dto.item.WeaponModeDto
import com.apocalypse.thefall.dto.item.instance.AmmoInstanceDto
import com.apocalypse.thefall.dto.item.instance.ArmorInstanceDto
import com.apocalypse.thefall.dto.item.instance.ItemInstanceDto
import com.apocalypse.thefall.dto.item.instance.WeaponInstanceDto
import com.apocalypse.thefall.entity.instance.AmmoInstance
import com.apocalypse.thefall.entity.instance.ArmorInstance
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.instance.WeaponInstance
import com.apocalypse.thefall.entity.item.Weapon
import com.apocalypse.thefall.mapper.item.toDto

/* =========================
   Dispatcher
   ========================= */

fun ItemInstance.toDto(): ItemInstanceDto? = when (this) {
    is WeaponInstance -> this.toDto()
    is ArmorInstance -> this.toDto()
    is AmmoInstance -> this.toDto()
    else -> null
}

/* =========================
   ArmorInstance
   ========================= */

fun ArmorInstance.toDto(): ArmorInstanceDto =
    ArmorInstanceDto(
        id = id,
        item = item?.toDto(),
        equippedSlot = equippedSlot
    )

/* =========================
   AmmoInstance
   ========================= */

fun AmmoInstance.toDto(): AmmoInstanceDto =
    AmmoInstanceDto(
        id = id,
        item = item?.toDto(),
        quantity = quantity
    )

/* =========================
   WeaponInstance
   ========================= */

fun WeaponInstance.toDto(): WeaponInstanceDto =
    WeaponInstanceDto(
        id = id,
        item = item?.toDto(),
        currentWeaponMode = resolveWeaponMode(),
        currentAmmoQuantity = currentAmmoQuantity,
        currentAmmoType = currentAmmoType?.toDto(),
        equippedSlot = equippedSlot
    )

/* =========================
   Helpers
   ========================= */

private fun WeaponInstance.resolveWeaponMode(): WeaponModeDto? {
    currentWeaponMode?.let { return it.toDto() }
    return (item as? Weapon)?.modes?.firstOrNull()?.toDto()
}
