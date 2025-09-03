package com.apocalypse.thefall.service.character.rules.perk.rule

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.rules.perk.PerkCode
import com.apocalypse.thefall.service.character.rules.perk.PerkRule
import org.springframework.stereotype.Component

@PerkCode(PerkEnum.ANIMAL_FRIEND)
@Component
class AnimalFriendRule : PerkRule {

    override fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int
    ): Boolean {
        val intelligence = specialMap[SpecialEnum.INTELLIGENCE] ?: 0
        val outdoorsman = skillValues[SkillEnum.OUTDOORSMAN] ?: 0

        return character.currentStats.level >= 9 &&
                intelligence >= 5 &&
                outdoorsman >= 25 &&
                rank < 1
    }
}
