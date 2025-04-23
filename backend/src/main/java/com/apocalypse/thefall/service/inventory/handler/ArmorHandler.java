package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.iteminstance.ArmorInstanceRepository;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ArmorHandler extends AbstractItemHandler<Armor, ArmorInstance> {
    private final ArmorInstanceRepository armorInstanceRepository;

    public ArmorHandler(ArmorInstanceRepository armorInstanceRepository, ItemInstanceFactory itemInstanceFactory, GameProperties gameProperties) {
        super(itemInstanceFactory, gameProperties);
        this.armorInstanceRepository = armorInstanceRepository;
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
    public void equip(Character character, ArmorInstance armorInstance, EquippedSlot slot) {
        if (slot.equals(armorInstance.getEquippedSlot())) {
            return; // Rien à faire
        }

        validateActionPoints(character, getEquipActionPointsCost());
        validateRequirements(character, (Armor) Hibernate.unproxy(armorInstance.getItem()));

        Inventory inventory = character.getInventory();

        // Déséquipe l'armure déjà équipée sur ce slot
        inventory.getItems().stream()
                .filter(i -> i instanceof ArmorInstance)
                .map(i -> (ArmorInstance) i)
                .filter(a -> slot.equals(a.getEquippedSlot()))
                .forEach(a -> {
                    a.setEquippedSlot(null);
                    armorInstanceRepository.save(a);
                });

        // Équipe la nouvelle armure
        armorInstance.setEquippedSlot(slot);
        armorInstanceRepository.save(armorInstance);

        consumeActionPoints(character, getEquipActionPointsCost());
    }

    @Override
    public void unequip(Character character, ArmorInstance armorInstance) {
        validateActionPoints(character, getUnequipActionPointsCost());

        if (armorInstance.getEquippedSlot() != EquippedSlot.ARMOR) {
            throw new GameException("error.game.armor.notEquipped", HttpStatus.BAD_REQUEST);
        }

        armorInstance.setEquippedSlot(null);
        armorInstanceRepository.save(armorInstance);

        consumeActionPoints(character, getUnequipActionPointsCost());
    }

}