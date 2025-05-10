package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SkillInstance;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Voir les règles de calculs…
 * <a href="https://fallout.fandom.com/wiki/Fallout_skills"></a>
 */
@Component
@RequiredArgsConstructor
public class CharacterSkillEngine {

    private final CharacterSkillRule skillRule;
    private final SpecialService specialService;

    public Map<SkillEnum, Integer> compute(Character character) {
        Map<SpecialEnum, Integer> specialValues = specialService.getSpecialValuesMap(character);
        Map<SkillEnum, Integer> result = new HashMap<>();

        for (SkillInstance instance : character.getSkills()) {
            SkillEnum code = instance.getSkill().getCode();
            int bonus = skillRule.apply(code, specialValues);
            int total = instance.getValue() + bonus;
            instance.setCalculatedValue(total);
            result.put(code, total);
        }
        return result;
    }
}
