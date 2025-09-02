package com.apocalypse.thefall.mapper.item

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.dto.item.*
import com.apocalypse.thefall.entity.item.*

/* =========================
   Item -> ItemDto (dispatcher)
   ========================= */

fun Item.toDto(): ItemDto? = when (this) {
    is Weapon -> this.toDto()
    is Armor -> this.toDto()
    is Ammo -> this.toDto()
    else -> null
}

/* =========================
   Weapon / WeaponMode
   ========================= */

fun Weapon.toDto(): WeaponDto =
    WeaponDto(
        id = this.id,
        type = this.type,
        names = this.names,
        descriptions = this.descriptions,
        weight = this.weight,
        basePrice = this.basePrice,
        path = this.path,
        weaponType = this.weaponType,
        requiredStrength = this.requiredStrength,
        requiredHands = this.requiredHands,
        capacity = this.capacity,
        damageType = this.damageType?.toDto(),
        weaponModes = this.modes.map { it.toDto() }.toMutableSet(),
        compatibleAmmo = this.compatibleAmmo.map { it.toDto() }.toMutableSet()
    )

fun WeaponMode.toDto(): WeaponModeDto =
    WeaponModeDto(
        id = this.id,
        modeType = this.modeType,
        actionPoints = this.actionPoints,
        minDamage = this.minDamage,
        maxDamage = this.maxDamage,
        range = this.range,
        shotsPerBurst = this.shotsPerBurst
    )

/* =========================
   Armor / ArmorDamage
   ========================= */

fun Armor.toDto(): ArmorDto =
    ArmorDto(
        id = this.id,
        type = this.type,
        names = this.names,
        descriptions = this.descriptions,
        weight = this.weight,
        basePrice = this.basePrice,
        path = this.path,
        armorClass = this.armorClass,
        damages = this.damages.map { it.toDto() }.toMutableSet()
    )

fun ArmorDamage.toDto(): ArmorDamageDto =
    ArmorDamageDto(
        id = this.id,
        threshold = this.threshold,
        resistance = this.resistance,
        damageType = this.damageType?.toDto()
    )

/* =========================
   DamageType -> DataItemDto
   ========================= */

fun DamageType.toDto(): DataItemDto =
    DataItemDto(
        id = this.id,
        code = this.code.name,
        names = this.names,
        descriptions = this.descriptions,
        imagePath = this.imagePath,
        displayOrder = this.displayOrder,
        visible = this.visible,
        shortNames = null,
        camelCaseCode = this.code.name
    )

/* =========================
   Ammo
   ========================= */

fun Ammo.toDto(): AmmoDto =
    AmmoDto(
        id = this.id,
        type = this.type,
        names = this.names,
        descriptions = this.descriptions,
        weight = this.weight,
        basePrice = this.basePrice,
        path = this.path,
        armorClassModifier = this.armorClassModifier,
        damageResistanceModifier = this.damageResistanceModifier,
        damageModifier = this.damageModifier,
        damageThresholdModifier = this.damageThresholdModifier
    )