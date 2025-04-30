package com.apocalypse.thefall.dto.character;

public record CharacterCurrentStatsDto(
        int actionPoints,
        int hitPoints,
        int level,
        CharacterStatusStatsDto status
) {
}