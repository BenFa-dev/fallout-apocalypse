package com.apocalypse.thefall.service.character.rules.perk.rule

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.rules.perk.PerkCode
import com.apocalypse.thefall.service.character.rules.perk.PerkRule
import org.springframework.stereotype.Component

@PerkCode(PerkEnum.HEALER)
@Component
class HealerRule : PerkRule {
    override fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int
    ): Boolean {
        val level = character.currentStats.level
        val perception = specialMap[SpecialEnum.PERCEPTION] ?: 0
        val intelligence = specialMap[SpecialEnum.INTELLIGENCE] ?: 0
        val agility = specialMap[SpecialEnum.AGILITY] ?: 0

        return level >= 3 &&
                perception >= 7 &&
                intelligence >= 5 &&
                agility >= 6 &&
                rank < 3 &&
                character.skills.any { it.skill?.code == SkillEnum.FIRST_AID && it.calculatedValue!! >= 40 }
    }
}
