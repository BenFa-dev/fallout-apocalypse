package com.apocalypse.thefall.service.character.rules.skill.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.skill.CharacterSkillRule;
import com.apocalypse.thefall.service.character.rules.skill.SkillCode;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@SkillCode(SkillEnum.DOCTOR)
@Component
@RequiredArgsConstructor
class DoctorRule implements CharacterSkillRule {
    private final SpecialService specialService;

    @Override
    public int apply(Character character) {
        int in = specialService.getSpecialValue(character, SpecialEnum.INTELLIGENCE);
        int pe = specialService.getSpecialValue(character, SpecialEnum.PERCEPTION);
        return 15 + ((pe + in) / 2);
    }
}
