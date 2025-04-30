package com.apocalypse.thefall.service.character.rules.stat.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStatRule;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerkRateRule implements CharacterStatRule {

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        builder.perkRate(3);
    }
}
