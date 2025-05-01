package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.stats.SpecialEnum;

import java.util.Map;

public interface CharacterSkillRule {
    int apply(Map<SpecialEnum, Integer> specialValues);
}

