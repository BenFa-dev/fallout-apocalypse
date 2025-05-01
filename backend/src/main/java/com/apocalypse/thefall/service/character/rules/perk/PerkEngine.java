package com.apocalypse.thefall.service.character.rules.perk;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.*;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillEngine;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import com.apocalypse.thefall.util.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Voir les règles de calculs…
 * <a href="https://fallout.fandom.com/wiki/Fallout_perks"></a>
 */
@Component
@RequiredArgsConstructor
public class PerkEngine {

    private final List<PerkRule> rules;
    private final SpecialService specialService;
    private final CharacterSkillEngine characterSkillEngine;

    public void compute(List<Perk> perks, Character character) {
        Map<PerkEnum, PerkRule> ruleByCode = buildRuleMap();
        Map<SpecialEnum, Integer> specialMap = specialService.getSpecialValuesMap(character);
        Map<PerkEnum, Integer> perkInstanceMap = MapUtils.extractMap(
                character.getPerks(),
                instance -> instance.getPerk() != null ? instance.getPerk().getCode() : null,
                PerkInstance::getValue
        );
        Map<SkillEnum, Integer> skillValues = characterSkillEngine.compute(character);

        Iterator<Perk> iterator = perks.iterator();
        while (iterator.hasNext()) {
            Perk perk = iterator.next();
            PerkRule rule = ruleByCode.get(perk.getCode());
            if (rule == null || !rule.apply(perk, character, specialMap, skillValues, perkInstanceMap.getOrDefault(perk.getCode(), 0))) {
                iterator.remove();
            }
        }
    }

    private Map<PerkEnum, PerkRule> buildRuleMap() {
        Map<PerkEnum, PerkRule> result = new HashMap<>();
        for (PerkRule rule : rules) {
            PerkCode perkCode = rule.getClass().getAnnotation(PerkCode.class);
            if (perkCode != null) {
                result.put(perkCode.value(), rule);
            }
        }
        return result;
    }

}
