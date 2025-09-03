package com.apocalypse.thefall.service.character.rules.perk.rule

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.rules.perk.PerkCode
import com.apocalypse.thefall.service.character.rules.perk.PerkRule
import org.springframework.stereotype.Component

@PerkCode(PerkEnum.BONUS_HTH_DAMAGE)
@Component
class BonusHthDamageRule : PerkRule {

    override fun apply(
        perk: Perk,
        character: Character,
        specialMap: Map<SpecialEnum, Int>,
        skillValues: Map<SkillEnum, Int>,
        rank: Int
    ): Boolean {
        val strength = specialMap[SpecialEnum.STRENGTH] ?: 0
        val agility = specialMap[SpecialEnum.AGILITY] ?: 0

        return character.currentStats.level >= 3 &&
                strength >= 6 &&
                agility >= 6 &&
                rank < 3
    }
}
