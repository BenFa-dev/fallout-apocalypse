package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@SkillCode(SkillEnum.OUTDOORSMAN)
@Component
@RequiredArgsConstructor
class OutdoorsmanRule implements CharacterSkillRule {

    @Override
    public int apply(Map<SpecialEnum, Integer> specialValues) {
        int in = specialValues.getOrDefault(SpecialEnum.INTELLIGENCE, 0);
        int en = specialValues.getOrDefault(SpecialEnum.ENDURANCE, 0);
        return 5 + ((in + en) / 2);
    }
}

