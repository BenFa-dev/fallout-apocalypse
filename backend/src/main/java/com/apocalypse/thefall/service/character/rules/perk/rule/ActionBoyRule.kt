package com.apocalypse.thefall.service.character.rules.perk.rule

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.rules.perk.PerkCode
import com.apocalypse.thefall.service.character.rules.perk.PerkRule
import org.springframework.stereotype.Component

@PerkCode(PerkEnum.ACTION_BOY)
@Component
class ActionBoyRule : PerkRule {

    override fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int?
    ): Boolean {
        val agility = specialMap.getOrDefault(SpecialEnum.AGILITY, 0)
        val level = character.currentStats?.level ?: 0
        return agility >= 5 && level >= 12 && (rank ?: 0) < 3
    }
}
