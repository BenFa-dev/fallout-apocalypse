package com.apocalypse.thefall.service.character.rules.perk;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;

import java.util.Map;

public interface PerkRule {
    boolean apply(Perk perk, Character character, Map<SpecialEnum, Integer> specialMap, Map<SkillEnum, Integer> skillValues, Integer rank);
}