package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SkillInstance;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Voir les règles de calculs…
 * <a href="https://fallout.fandom.com/wiki/Fallout_skills"></a>
 */
@Component
@RequiredArgsConstructor
public class CharacterSkillEngine {

    private final List<CharacterSkillRule> rules;
    private final SpecialService specialService;

    public Map<SkillEnum, Integer> compute(Character character) {

        Map<SkillEnum, CharacterSkillRule> ruleByCode = buildRuleMap();
        Map<SpecialEnum, Integer> specialValues = specialService.getSpecialValuesMap(character);

        Map<SkillEnum, Integer> result = new HashMap<>();

        for (SkillInstance instance : character.getSkills()) {
            SkillEnum code = instance.getSkill().getCode();
            CharacterSkillRule rule = ruleByCode.get(code);
            int bonus = rule != null ? rule.apply(specialValues) : 0;
            int total = instance.getValue() + bonus;
            instance.setCalculatedValue(total);
            result.put(code, total);
        }
        return result;
    }

    private Map<SkillEnum, CharacterSkillRule> buildRuleMap() {
        Map<SkillEnum, CharacterSkillRule> map = new HashMap<>();
        for (CharacterSkillRule rule : rules) {
            SkillCode annotation = rule.getClass().getAnnotation(SkillCode.class);
            if (annotation != null) {
                map.put(annotation.value(), rule);
            }
        }
        return map;
    }
}
