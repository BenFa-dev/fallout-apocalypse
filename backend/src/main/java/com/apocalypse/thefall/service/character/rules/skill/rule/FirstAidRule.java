package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@SkillCode(SkillEnum.FIRST_AID)
@Component
@RequiredArgsConstructor
class FirstAidRule implements CharacterSkillRule {

    @Override
    public int apply(Map<SpecialEnum, Integer> specialValues) {
        int in = specialValues.getOrDefault(SpecialEnum.INTELLIGENCE, 0);
        int pe = specialValues.getOrDefault(SpecialEnum.PERCEPTION, 0);
        return 30 + ((pe + in) / 2);
    }
}
