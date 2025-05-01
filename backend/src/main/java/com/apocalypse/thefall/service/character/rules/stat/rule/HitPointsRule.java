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
public class HitPointsRule implements CharacterStatRule {

    @Override
    public void apply(Set<ItemInstance> items, Map<SpecialEnum, Integer> specialValues, CharacterStats.CharacterStatsBuilder builder) {
        builder.hitPoints(15
                + specialValues.getOrDefault(SpecialEnum.STRENGTH, 0)
                + (2 * specialValues.getOrDefault(SpecialEnum.ENDURANCE, 0)));
    }
}
