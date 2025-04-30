package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@SkillCode(SkillEnum.ENERGY_WEAPONS)
@Component
@RequiredArgsConstructor
class EnergyWeaponsRule implements CharacterSkillRule {
    private final SpecialService specialService;

    @Override
    public int apply(Character character) {
        int agility = specialService.getSpecialValue(character, SpecialEnum.AGILITY);
        return 10 + agility;
    }
}
