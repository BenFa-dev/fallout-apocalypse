package com.apocalypse.thefall.service.character.rules.stat;

import com.apocalypse.thefall.entity.character.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterStatEngine {

    private final List<CharacterStatRule> rules;

    public CharacterStats compute(Character character) {
        CharacterStats.CharacterStatsBuilder builder = CharacterStats.builder();
        rules.forEach(rule -> rule.apply(character, builder));
        return builder.build();
    }
}