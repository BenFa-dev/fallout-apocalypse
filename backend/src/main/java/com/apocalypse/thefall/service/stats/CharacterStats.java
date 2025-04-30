package com.apocalypse.thefall.service.stats;

import com.apocalypse.thefall.dto.item.ArmorDamageDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record CharacterStats(
        int actionPoints,
        int armorClass,
        int carryWeight,
        int criticalChance,
        int healingRate,
        int hitPoints,
        int meleeDamage,
        int partyLimit,
        int perkRate,
        int poisonResistance,
        int radiationResistance,
        int sequence,
        int skillPoints,
        Set<ArmorDamageDto> damages
) {
}