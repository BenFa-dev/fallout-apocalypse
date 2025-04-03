package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Weapon;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WeaponHandler extends AbstractItemHandler<Weapon, WeaponInstance> {

    public WeaponHandler(
            ItemInstanceFactory itemInstanceFactory,
            GameProperties gameProperties) {
        super(itemInstanceFactory, gameProperties);
    }

    @Override
    public WeaponInstance createInstance(Weapon weapon) {
        return (WeaponInstance) itemInstanceFactory.createInstance(weapon);
    }

    @Override
    public void validateRequirements(Character character, Weapon weapon) {
        if (character.getStrength() < weapon.getRequiredStrength()) {
            throw new GameException("error.game.weapon.notEnoughStrength", HttpStatus.BAD_REQUEST,
                    String.valueOf(weapon.getRequiredStrength()));
        }
    }

    @Override
    public void equip(Character character, WeaponInstance weaponInstance) {
        validateActionPoints(character, getEquipActionPointsCost());
        validateRequirements(character, (Weapon) weaponInstance.getItem());

        Inventory inventory = character.getInventory();

        boolean hasPrimary = inventory.getItems().stream()
                .filter(i -> i instanceof WeaponInstance)
                .map(i -> (WeaponInstance) i)
                .anyMatch(w -> w.getEquippedSlot() == EquippedSlot.PRIMARY_WEAPON);

        boolean hasSecondary = inventory.getItems().stream()
                .filter(i -> i instanceof WeaponInstance)
                .map(i -> (WeaponInstance) i)
                .anyMatch(w -> w.getEquippedSlot() == EquippedSlot.SECONDARY_WEAPON);

        if (!hasPrimary) {
            weaponInstance.setEquippedSlot(EquippedSlot.PRIMARY_WEAPON);
        } else if (!hasSecondary) {
            weaponInstance.setEquippedSlot(EquippedSlot.SECONDARY_WEAPON);
        } else {
            throw new GameException("error.game.weapon.allSlotsOccupied", HttpStatus.BAD_REQUEST);
        }

        consumeActionPoints(character, getEquipActionPointsCost());
    }

    @Override
    public void unequip(Character character, WeaponInstance weaponInstance) {
        validateActionPoints(character, getUnequipActionPointsCost());

        if (weaponInstance.getEquippedSlot() == null) {
            throw new GameException("error.game.weapon.notEquipped", HttpStatus.BAD_REQUEST);
        }

        weaponInstance.setEquippedSlot(null);
        consumeActionPoints(character, getUnequipActionPointsCost());
    }


}