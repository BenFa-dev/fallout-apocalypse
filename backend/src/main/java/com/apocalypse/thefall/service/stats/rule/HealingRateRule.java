package com.apocalypse.thefall.service.stats.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import com.apocalypse.thefall.service.stats.CharacterStatRule;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HealingRateRule implements CharacterStatRule {
    private final SpecialService specialService;

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        int endurance = specialService.getSpecialValue(character, SpecialEnum.ENDURANCE);
        int healingRate = Math.max(1, endurance / 3);
        builder.healingRate(healingRate);
    }
}
