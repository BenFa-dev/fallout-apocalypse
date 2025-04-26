package com.apocalypse.thefall.service.stats.rule;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.service.stats.CharacterStatRule;
import com.apocalypse.thefall.service.stats.CharacterStats;
import org.springframework.stereotype.Component;

@Component
public class MaxActionPointsRule implements CharacterStatRule {
    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        int ap = 5 + character.getSpecial().getAgility() / 2;
        builder.maxActionPoints(ap);
    }
}