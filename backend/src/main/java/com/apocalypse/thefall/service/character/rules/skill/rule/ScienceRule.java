package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@SkillCode(SkillEnum.SCIENCE)
@Component
@RequiredArgsConstructor
class ScienceRule implements CharacterSkillRule {

    @Override
    public int apply(Map<SpecialEnum, Integer> specialValues) {
        return 25 + 2 * specialValues.getOrDefault(SpecialEnum.INTELLIGENCE, 0);
    }
}
