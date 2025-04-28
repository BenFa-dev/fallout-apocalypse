package com.apocalypse.thefall.service.stats;

import com.apocalypse.thefall.entity.character.Character;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CharacterStatEngine {

    private final List<CharacterStatRule> rules;

    public CharacterStatEngine(List<CharacterStatRule> rules) {
        this.rules = rules;
    }

    public CharacterStats compute(Character character) {
        CharacterStats.CharacterStatsBuilder builder = CharacterStats.builder();
        rules.forEach(rule -> rule.apply(character, builder));
        return builder.build();
    }
}