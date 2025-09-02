package com.apocalypse.thefall.dto.item

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.entity.item.enums.ItemType
import com.apocalypse.thefall.entity.item.enums.WeaponType

@JvmRecord
data class WeaponDto(
    override val id: Long?,
    override val type: ItemType?,
    override val names: MutableMap<String, String>?,
    override val descriptions: MutableMap<String, String>?,
    override val weight: Double?,
    override val basePrice: Int?,
    override val path: String?,
    val weaponType: WeaponType?,
    val requiredStrength: Int,
    val requiredHands: Int,
    val capacity: Int?,
    val damageType: DataItemDto?,
    val weaponModes: MutableSet<WeaponModeDto?>?,
    val compatibleAmmo: MutableSet<AmmoDto?>?
) : ItemDto 