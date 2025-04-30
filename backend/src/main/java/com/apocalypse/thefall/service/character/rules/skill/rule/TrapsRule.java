package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@SkillCode(SkillEnum.TRAPS)
@Component
@RequiredArgsConstructor
class TrapsRule implements CharacterSkillRule {
    private final SpecialService specialService;

    @Override
    public int apply(Character character) {
        int ag = specialService.getSpecialValue(character, SpecialEnum.AGILITY);
        int pe = specialService.getSpecialValue(character, SpecialEnum.PERCEPTION);
        return 20 + ((pe + ag) / 2);
    }
}
