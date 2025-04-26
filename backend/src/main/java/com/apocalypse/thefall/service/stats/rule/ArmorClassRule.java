package com.apocalypse.thefall.service.stats.rule;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.mapper.item.ItemMapper;
import com.apocalypse.thefall.service.stats.CharacterStatRule;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArmorClassRule implements CharacterStatRule {

    private final ItemMapper itemMapper;

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        character.getInventory().getItems().stream()
                .filter(ArmorInstance.class::isInstance)
                .map(ArmorInstance.class::cast)
                .filter(a -> a.getEquippedSlot() == EquippedSlot.ARMOR)
                .findFirst()
                .ifPresent(equippedArmor -> {
                    Armor armor = (Armor) equippedArmor.getItem();
                    builder.armorClass(armor.getArmorClass());
                    builder.damages(armor.getDamages().stream()
                            .map(itemMapper::toDto)
                            .collect(Collectors.toSet()));
                });
    }
}