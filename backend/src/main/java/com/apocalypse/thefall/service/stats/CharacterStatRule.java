package com.apocalypse.thefall.service.stats;

import com.apocalypse.thefall.entity.Character;

public interface CharacterStatRule {
    void apply(Character character, CharacterStats.CharacterStatsBuilder builder);
}