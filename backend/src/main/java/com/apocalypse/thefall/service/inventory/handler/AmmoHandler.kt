package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Ammo;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.Weapon;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.iteminstance.AmmoInstanceRepository;
import com.apocalypse.thefall.repository.iteminstance.WeaponInstanceRepository;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AmmoHandler extends AbstractItemHandler<Ammo, AmmoInstance> {
    private final WeaponInstanceRepository weaponInstanceRepository;
    private final AmmoInstanceRepository ammoInstanceRepository;

    public AmmoHandler(
            WeaponInstanceRepository weaponInstanceRepository,
            AmmoInstanceRepository ammoInstanceRepository,
            ItemInstanceFactory itemInstanceFactory,
            GameProperties gameProperties
    ) {
        super(itemInstanceFactory, gameProperties);
        this.weaponInstanceRepository = weaponInstanceRepository;
        this.ammoInstanceRepository = ammoInstanceRepository;
    }

    @Override
    public AmmoInstance createInstance(Ammo ammo) {
        return (AmmoInstance) itemInstanceFactory.createInstance(ammo);
    }

    @Override
    public void validateRequirements(Character character, Ammo ammo) {
        validateWeight(character, ammo);
    }

    @Override
    public void equip(Character character, AmmoInstance ammoInstance, EquippedSlot slot) {
        throw new GameException("error.game.ammo.cannotEquip", HttpStatus.BAD_REQUEST);
    }

    @Override
    public void unequip(Character character, AmmoInstance ammoInstance) {
        throw new GameException("error.game.ammo.cannotUnequip", HttpStatus.BAD_REQUEST);
    }

    protected int getReloadActionPointsCost() {
        return gameProperties.getInventory().getActionPoints().getReload();
    }

    protected int getUnloadActionPointsCost() {
        return gameProperties.getInventory().getActionPoints().getUnload();
    }

    public void reloadWeapon(Character character, WeaponInstance weaponInstance, AmmoInstance ammoInstance) {
        validateActionPoints(character, getReloadActionPointsCost());

        Item weaponItem = (Item) Hibernate.unproxy(weaponInstance.getItem());
        Item ammoItem = (Item) Hibernate.unproxy(ammoInstance.getItem());
        if (weaponItem instanceof Weapon weapon && ammoItem instanceof Ammo ammo) {

            if (!weapon.getCompatibleAmmo().contains(ammoItem)) {
                throw new GameException("error.game.weapon.incompatibleAmmo", HttpStatus.BAD_REQUEST);
            }

            // On décharge s'il on avait un autre type de munition
            if (weaponInstance.getCurrentAmmoType() != null && !weaponInstance.getCurrentAmmoType().getId().equals(ammoInstance.getItem().getId())) {
                unloadWeapon(character, weaponInstance);
            }

            weaponInstance.setCurrentAmmoType(ammo);
            int quantityToLoad = Math.min(ammoInstance.getQuantity(), weapon.getCapacity());
            weaponInstance.setCurrentAmmoQuantity(quantityToLoad);
            weaponInstanceRepository.save(weaponInstance);

            ammoInstance.setQuantity(ammoInstance.getQuantity() - quantityToLoad);
            if (ammoInstance.getQuantity() <= 0) {
                ammoInstanceRepository.delete(ammoInstance);
                character.getInventory().getItems().remove(ammoInstance);
            } else {
                ammoInstanceRepository.save(ammoInstance);
            }
        }

        consumeActionPoints(character, getReloadActionPointsCost());
    }

    public void unloadWeapon(Character character, WeaponInstance weapon) {
        validateActionPoints(character, getUnloadActionPointsCost());

        if (weapon.getCurrentAmmoQuantity() <= 0) {
            throw new GameException("error.game.weapon.noAmmoToUnload", HttpStatus.BAD_REQUEST);
        }

        Inventory inventory = character.getInventory();
        Ammo ammoType = weapon.getCurrentAmmoType();

        // Cherche une instance existante de cette munition dans l'inventaire
        Optional<AmmoInstance> existingInstanceOpt = inventory.getItems().stream()
                .filter(i -> i instanceof AmmoInstance)
                .map(i -> (AmmoInstance) i)
                .filter(a -> ammoType.equals(a.getItem()))
                .findFirst();

        if (existingInstanceOpt.isPresent()) {
            AmmoInstance existing = existingInstanceOpt.get();
            existing.setQuantity(existing.getQuantity() + weapon.getCurrentAmmoQuantity());
            ammoInstanceRepository.save(existing);
        } else {
            AmmoInstance newInstance = (AmmoInstance) itemInstanceFactory.createInstance(ammoType);
            newInstance.setQuantity(weapon.getCurrentAmmoQuantity());
            newInstance.setInventory(inventory);
            AmmoInstance t = ammoInstanceRepository.save(newInstance);
            character.getInventory().getItems().add(t);
        }

        weapon.setCurrentAmmoType(null);
        weapon.setCurrentAmmoQuantity(0);
        weaponInstanceRepository.save(weapon);

        consumeActionPoints(character, getUnloadActionPointsCost());
    }
}