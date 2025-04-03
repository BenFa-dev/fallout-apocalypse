package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.item.Ammo;
import com.apocalypse.thefall.entity.item.Weapon;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AmmoHandler extends AbstractItemHandler<Ammo, AmmoInstance> {

    public AmmoHandler(ItemInstanceFactory itemInstanceFactory, GameProperties gameProperties) {
        super(itemInstanceFactory, gameProperties);
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
    public void equip(Character character, AmmoInstance ammoInstance) {
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

    public void reloadWeapon(Character character, WeaponInstance weapon, AmmoInstance ammo) {
        validateActionPoints(character, getReloadActionPointsCost());

        Weapon weaponItem = (Weapon) weapon.getItem();
        Ammo ammoItem = (Ammo) ammo.getItem();

        if (!weaponItem.getCompatibleAmmo().contains(ammoItem)) {
            throw new GameException("error.game.weapon.incompatibleAmmo", HttpStatus.BAD_REQUEST);
        }

        if (weapon.getCurrentAmmoQuantity() > 0) {
            throw new GameException("error.game.weapon.alreadyLoaded", HttpStatus.BAD_REQUEST);
        }

        if (ammo.getQuantity() <= 0) {
            throw new GameException("error.game.ammo.noAmmoLeft", HttpStatus.BAD_REQUEST);
        }

        weapon.setCurrentAmmoType(ammoItem);
        weapon.setCurrentAmmoQuantity(Math.min(ammo.getQuantity(), weaponItem.getCapacity()));
        ammo.setQuantity(ammo.getQuantity() - weapon.getCurrentAmmoQuantity());

        consumeActionPoints(character, getReloadActionPointsCost());
    }

    public void unloadWeapon(Character character, WeaponInstance weapon) {
        validateActionPoints(character, getUnloadActionPointsCost());

        if (weapon.getCurrentAmmoQuantity() <= 0) {
            throw new GameException("error.game.weapon.noAmmoToUnload", HttpStatus.BAD_REQUEST);
        }

        AmmoInstance ammoInstance = createInstance(weapon.getCurrentAmmoType());
        ammoInstance.setQuantity(weapon.getCurrentAmmoQuantity());

        weapon.setCurrentAmmoType(null);
        weapon.setCurrentAmmoQuantity(0);

        character.getInventory().getItems().add(ammoInstance);
        consumeActionPoints(character, getUnloadActionPointsCost());
    }
}