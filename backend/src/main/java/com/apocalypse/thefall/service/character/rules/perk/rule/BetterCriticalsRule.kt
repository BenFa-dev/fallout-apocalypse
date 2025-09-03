package com.apocalypse.thefall.service.character.rules.perk.rule

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.rules.perk.PerkCode
import com.apocalypse.thefall.service.character.rules.perk.PerkRule
import org.springframework.stereotype.Component

@PerkCode(PerkEnum.BETTER_CRITICALS)
@Component
class BetterCriticalsRule : PerkRule {

    override fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int
    ): Boolean {
        val perception = specialMap[SpecialEnum.PERCEPTION] ?: 0
        val agility = specialMap[SpecialEnum.AGILITY] ?: 0
        val luck = specialMap[SpecialEnum.LUCK] ?: 0

        return character.currentStats.level >= 9 &&
                perception >= 6 &&
                agility >= 4 &&
                luck >= 6 &&
                rank < 1
    }
}
