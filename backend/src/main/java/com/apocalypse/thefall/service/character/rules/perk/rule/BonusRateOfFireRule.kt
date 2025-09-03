package com.apocalypse.thefall.service.character.rules.perk.rule

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.rules.perk.PerkCode
import com.apocalypse.thefall.service.character.rules.perk.PerkRule
import org.springframework.stereotype.Component

@PerkCode(PerkEnum.BONUS_RATE_OF_FIRE)
@Component
class BonusRateOfFireRule : PerkRule {
    override fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int
    ) =
        character.currentStats.level >= 9 &&
                (specialMap[SpecialEnum.PERCEPTION] ?: 0) >= 6 &&
                (specialMap[SpecialEnum.INTELLIGENCE] ?: 0) >= 6 &&
                (specialMap[SpecialEnum.AGILITY] ?: 0) >= 7 &&
                rank < 1
}
