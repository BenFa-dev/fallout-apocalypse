package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;

import java.util.Map;

public interface CharacterSkillRule {
    int apply(SkillEnum skillCode, Map<SpecialEnum, Integer> specialValues);
}
