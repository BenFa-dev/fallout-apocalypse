package com.apocalypse.thefall.dto.item

import com.apocalypse.thefall.entity.item.enums.WeaponModeType

@JvmRecord
data class WeaponModeDto(
    val id: Long?,
    val modeType: WeaponModeType?,
    val actionPoints: Int?,
    val minDamage: Int?,
    val maxDamage: Int?,
    val range: Int?,
    val shotsPerBurst: Int?
) 