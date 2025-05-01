package com.apocalypse.thefall.service.character.rules.stat.rule;

import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStatRule;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PerkRateRule implements CharacterStatRule {

    @Override
    public void apply(Set<ItemInstance> items, Map<SpecialEnum, Integer> specialValues, CharacterStats.CharacterStatsBuilder builder) {
        builder.perkRate(3);
    }
}
