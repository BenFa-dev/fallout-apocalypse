package com.apocalypse.thefall.service.character.rules.stat;

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ItemInstance;

import java.util.Map;
import java.util.Set;

public interface CharacterStatRule {
    void apply(Set<ItemInstance> items, Map<SpecialEnum, Integer> specialValues, CharacterStats.CharacterStatsBuilder builder);
}