package com.apocalypse.thefall.service.stats.rule;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.service.stats.CharacterStatRule;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PoisonResistanceRule implements CharacterStatRule {

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        int endurance = character.getSpecial().getEndurance();
        builder.poisonResistance(endurance * 5);
    }
}
