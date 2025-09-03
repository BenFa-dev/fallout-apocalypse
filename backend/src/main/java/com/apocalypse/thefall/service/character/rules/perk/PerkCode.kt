package com.apocalypse.thefall.service.character.rules.perk

import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PerkCode(val value: PerkEnum)
