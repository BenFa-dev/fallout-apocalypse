package com.apocalypse.thefall.service.character.rules.perk

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.entity.character.stats.PerkInstance
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.service.character.stats.SpecialService
import com.apocalypse.thefall.util.MapUtils
import org.springframework.stereotype.Component

@Component
open class PerkEngine(
    private val rules: List<PerkRule>,
    private val specialService: SpecialService
    // private val characterSkillEngine: CharacterSkillEngine
) {

    fun compute(perks: MutableList<Perk>, character: Character) {
        val ruleByCode = buildRuleMap()
        val specialMap: Map<SpecialEnum, Int> = specialService.getSpecialValuesMap(character)

        val perkInstanceMap: Map<PerkEnum, Int> = MapUtils.extractMap(
            character.perks,
            { inst: PerkInstance -> inst.perk?.code },
            PerkInstance::value
        )

        // val skillValues: Map<SkillEnum, Int> = characterSkillEngine.compute(character)

        val it = perks.iterator()
        while (it.hasNext()) {
            val perk = it.next()
            val rule = ruleByCode[perk.code]
            // if (rule == null || !rule.apply(perk, character, specialMap, skillValues, perkInstanceMap.getOrDefault(perk.code, 0))) {
            //     it.remove()
            // }
        }
    }

    private fun buildRuleMap(): Map<PerkEnum, PerkRule> =
        rules.mapNotNull { rule ->
            val ann = rule::class.java.getAnnotation(PerkCode::class.java)
            ann?.value?.let { it to rule }
        }.toMap()
}
