package com.apocalypse.thefall.service.stats;

import lombok.Builder;

@Builder
public record CharacterCurrentStats(
        int actionPoints,
        int hitPoints
) {
}