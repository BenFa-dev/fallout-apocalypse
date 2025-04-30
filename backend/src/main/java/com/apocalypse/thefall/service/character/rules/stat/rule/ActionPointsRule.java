package com.apocalypse.thefall.service.character.rules.stat.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStatRule;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionPointsRule implements CharacterStatRule {
    private final SpecialService specialService;

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        int ap = 5 + specialService.getSpecialValue(character, SpecialEnum.AGILITY) / 2;
        builder.actionPoints(ap);
    }
}