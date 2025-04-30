package com.apocalypse.thefall.service.stats.rule;

import com.apocalypse.thefall.dto.item.ArmorDamageDto;
import com.apocalypse.thefall.dto.item.DamageTypeDto;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.mapper.item.ItemMapper;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import com.apocalypse.thefall.service.inventory.DamageTypeService;
import com.apocalypse.thefall.service.stats.CharacterStatRule;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArmorClassRule implements CharacterStatRule {

    private final ItemMapper itemMapper;
    private final DamageTypeService damageTypeService;
    private final SpecialService specialService;

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        int agility = specialService.getSpecialValue(character, SpecialEnum.AGILITY);

        // Récupère l'armure équipée si présente
        Armor armor = character.getInventory().getItems().stream()
                .filter(ArmorInstance.class::isInstance)
                .map(ArmorInstance.class::cast)
                .filter(a -> a.getEquippedSlot() == EquippedSlot.ARMOR)
                .findFirst()
                .map(armorInstance -> (Armor) armorInstance.getItem())
                .orElse(null);

        // Calcule l'Armor Class (Agilité + armure si présente)
        int armorClass = agility + (armor != null ? armor.getArmorClass() : 0);
        builder.armorClass(armorClass);

        // Construit la liste complète de protections pour chaque DamageType
        Set<ArmorDamageDto> damages = damageTypeService.findAll().stream()
                .map(damageType -> {
                    if (armor != null) {
                        // Si l'armure a une valeur spécifique pour ce DamageType, l'utiliser
                        return armor.getDamages().stream()
                                .filter(ad -> ad.getDamageType().getId().equals(damageType.getId()))
                                .findFirst()
                                .map(itemMapper::toDto)
                                .orElseGet(() -> defaultArmorDamageDto(itemMapper.toDto(damageType)));
                    } else {
                        return defaultArmorDamageDto(itemMapper.toDto(damageType));
                    }
                })
                .collect(Collectors.toSet());

        builder.damages(damages);
    }

    private ArmorDamageDto defaultArmorDamageDto(DamageTypeDto damageTypeDto) {
        return ArmorDamageDto.builder()
                .id(null)
                .damageType(damageTypeDto)
                .threshold(0)
                .resistance(0)
                .build();
    }
}
