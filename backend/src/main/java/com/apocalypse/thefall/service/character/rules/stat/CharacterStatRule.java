package com.apocalypse.thefall.service.character.rules.stat;

import com.apocalypse.thefall.entity.character.Character;

public interface CharacterStatRule {
    void apply(Character character, CharacterStats.CharacterStatsBuilder builder);
}