package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.Weapon;
import com.apocalypse.thefall.entity.item.WeaponMode;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.iteminstance.WeaponInstanceRepository;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeaponHandler extends AbstractItemHandler<Weapon, WeaponInstance> {
    private final WeaponInstanceRepository weaponInstanceRepository;

    public WeaponHandler(WeaponInstanceRepository weaponInstanceRepository, ItemInstanceFactory itemInstanceFactory, GameProperties gameProperties) {
        super(itemInstanceFactory, gameProperties);
        this.weaponInstanceRepository = weaponInstanceRepository;
    }

    @Override
    public WeaponInstance createInstance(Weapon weapon) {
        return (WeaponInstance) itemInstanceFactory.createInstance(weapon);
    }

    @Override
    public void validateRequirements(Character character, Weapon weapon) {
        if (character.getSpecial().getStrength() < weapon.getRequiredStrength()) {
            throw new GameException("error.game.weapon.notEnoughStrength", HttpStatus.BAD_REQUEST,
                    String.valueOf(weapon.getRequiredStrength()));
        }
    }

    public void equip(Character character, WeaponInstance weaponInstance, EquippedSlot targetSlot) {
        if (targetSlot.equals(weaponInstance.getEquippedSlot())) return; // déjà équipé

        Item item = (Item) Hibernate.unproxy(weaponInstance.getItem());
        if (item instanceof Weapon weapon) {
            validateActionPoints(character, getEquipActionPointsCost());
            validateRequirements(character, weapon);
        }

        Inventory inventory = character.getInventory();

        // arme déjà présente dans le slot cible
        WeaponInstance existing = inventory.getItems().stream()
                .filter(i -> i instanceof WeaponInstance w && targetSlot.equals(w.getEquippedSlot()))
                .map(i -> (WeaponInstance) i)
                .findFirst()
                .orElse(null);

        EquippedSlot previousSlot = weaponInstance.getEquippedSlot(); // ancien slot
        weaponInstance.setEquippedSlot(targetSlot); // équipe nouvelle
        if (existing != null) existing.setEquippedSlot(previousSlot); // swap si existant

        weaponInstanceRepository.saveAll(existing != null ? List.of(weaponInstance, existing) : List.of(weaponInstance));

        consumeActionPoints(character, getEquipActionPointsCost());
    }


    @Override
    public void unequip(Character character, WeaponInstance weaponInstance) {
        validateActionPoints(character, getUnequipActionPointsCost());

        if (weaponInstance.getEquippedSlot() == null) {
            throw new GameException("error.game.weapon.notEquipped", HttpStatus.BAD_REQUEST);
        }

        weaponInstance.setEquippedSlot(null);
        weaponInstanceRepository.save(weaponInstance);

        consumeActionPoints(character, getUnequipActionPointsCost());
    }

    public void changeWeaponMode(WeaponInstance weaponInstance, WeaponMode weaponMode) {
        weaponInstance.setCurrentWeaponMode(weaponMode);
    }
}