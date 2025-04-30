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
public class PartyLimitRule implements CharacterStatRule {
    private final SpecialService specialService;

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        int charisma = specialService.getSpecialValue(character, SpecialEnum.CHARISMA);
        builder.partyLimit(charisma / 2);
    }
}
