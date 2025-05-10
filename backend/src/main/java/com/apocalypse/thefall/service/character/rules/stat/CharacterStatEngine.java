package com.apocalypse.thefall.service.character.rules.stat;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CharacterStatEngine {
    private final List<CharacterStatRule> rules;
    private final SpecialService specialService;

    public CharacterStats compute(Character character) {
        Map<SpecialEnum, Integer> specialMap = specialService.getSpecialValuesMap(character);

        CharacterStats.CharacterStatsBuilder builder = CharacterStats.builder();
        rules.forEach(rule -> rule.apply(character.getInventory().getItems(), specialMap, builder));
        return builder.build();
    }
}