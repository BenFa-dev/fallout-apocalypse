package com.apocalypse.thefall.service.character.rules.perk

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum

interface PerkRule {
    fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int
    ): Boolean
}
