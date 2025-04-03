package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ArmorHandler extends AbstractItemHandler<Armor, ArmorInstance> {

    public ArmorHandler(ItemInstanceFactory itemInstanceFactory, GameProperties gameProperties) {
        super(itemInstanceFactory, gameProperties);
    }

    @Override
    public ArmorInstance createInstance(Armor armor) {
        return (ArmorInstance) itemInstanceFactory.createInstance(armor);
    }

    @Override
    public void validateRequirements(Character character, Armor armor) {
        validateActionPoints(character, getEquipActionPointsCost());
        validateWeight(character, armor);
    }

    @Override
    public void equip(Character character, ArmorInstance armorInstance) {
        validateActionPoints(character, getEquipActionPointsCost());
        validateRequirements(character, (Armor) armorInstance.getItem());

        boolean hasArmorEquipped = character.getInventory().getItems().stream()
                .filter(i -> i instanceof ArmorInstance)
                .map(i -> (ArmorInstance) i)
                .anyMatch(a -> a.getEquippedSlot() == EquippedSlot.ARMOR);

        if (hasArmorEquipped) {
            throw new GameException("error.game.armor.alreadyEquipped", HttpStatus.BAD_REQUEST);
        }

        armorInstance.setEquippedSlot(EquippedSlot.ARMOR);
        consumeActionPoints(character, getEquipActionPointsCost());
    }

    @Override
    public void unequip(Character character, ArmorInstance armorInstance) {
        validateActionPoints(character, getUnequipActionPointsCost());

        if (armorInstance.getEquippedSlot() != EquippedSlot.ARMOR) {
            throw new GameException("error.game.armor.notEquipped", HttpStatus.BAD_REQUEST);
        }

        armorInstance.setEquippedSlot(null);
        consumeActionPoints(character, getUnequipActionPointsCost());
    }

}