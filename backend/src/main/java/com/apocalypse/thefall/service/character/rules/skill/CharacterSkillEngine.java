package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SkillInstance;
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

    public void compute(Character character) {
        Map<SkillEnum, CharacterSkillRule> ruleByCode = buildRuleMap();

        for (SkillInstance instance : character.getSkills()) {
            SkillEnum code = instance.getSkill().getCode();
            CharacterSkillRule rule = ruleByCode.get(code);
            int bonus = rule != null ? rule.apply(character) : 0;
            instance.setCalculatedValue(instance.getValue() + bonus);
        }
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
