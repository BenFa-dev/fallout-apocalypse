package com.apocalypse.thefall.service.character.rules.stat.rule;

import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStatRule;
import com.apocalypse.thefall.service.character.rules.stat.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ArmorClassRule implements CharacterStatRule {

    @Override
    public void apply(Set<ItemInstance> items, Map<SpecialEnum, Integer> specialValues, CharacterStats.CharacterStatsBuilder builder) {
        int agility = specialValues.getOrDefault(SpecialEnum.AGILITY, 0);

        // Récupère l'armure équipée si présente
        Armor armor = items.stream()
                .filter(ArmorInstance.class::isInstance)
                .map(ArmorInstance.class::cast)
                .filter(a -> a.getEquippedSlot() == EquippedSlot.ARMOR)
                .findFirst()
                .map(armorInstance -> (Armor) armorInstance.getItem())
                .orElse(null);

        // Calcule l'Armor Class (Agilité + armure si présente)
        int armorClass = agility + (armor != null ? armor.getArmorClass() : 0);
        builder.armorClass(armorClass);
        builder.damages(armor != null ? armor.getDamages() : Set.of());
    }
}
