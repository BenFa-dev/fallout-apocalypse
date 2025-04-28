package com.apocalypse.thefall.dto.character;

public record CharacterStatusStatsDto(
        boolean poisoned,
        boolean radiated,
        boolean eyeDamage,
        boolean rightArmCrippled,
        boolean leftArmCrippled,
        boolean rightLegCrippled,
        boolean leftLegCrippled
) {
}