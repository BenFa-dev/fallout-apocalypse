package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@SkillCode(SkillEnum.ENERGY_WEAPONS)
@Component
@RequiredArgsConstructor
class EnergyWeaponsRule implements CharacterSkillRule {

    @Override
    public int apply(Map<SpecialEnum, Integer> specialValues) {
        int agility = specialValues.getOrDefault(SpecialEnum.AGILITY, 0);
        return 10 + agility;
    }
}
