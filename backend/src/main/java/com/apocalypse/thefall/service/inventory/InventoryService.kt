package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.item.*;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.iteminstance.ItemInstanceRepository;
import com.apocalypse.thefall.service.character.CharacterService;
import com.apocalypse.thefall.service.inventory.handler.AmmoHandler;
import com.apocalypse.thefall.service.inventory.handler.ArmorHandler;
import com.apocalypse.thefall.service.inventory.handler.WeaponHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final AmmoHandler ammoHandler;
    private final ArmorHandler armorHandler;
    private final CharacterService characterService;
    private final ItemInstanceRepository itemInstanceRepository;
    private final ItemService itemService;
    private final WeaponHandler weaponHandler;
    private final WeaponModeService weaponModeService;

    @Transactional(readOnly = true)
    public Character get(Long characterId) {
        return characterService.getCharacterById(characterId);
    }

    @Transactional(readOnly = true)
    public Character get(String userId) {
        return characterService.getCharacterByUserId(userId);
    }

    @Transactional
    public Character addItem(Long characterId, Long itemId, Integer quantity) {
        Character character = characterService.getCharacterById(characterId);
        return doAddItem(character, itemId, quantity);
    }

    @Transactional
    public Character addItem(String userId, Long itemId, Integer quantity) {
        Character character = characterService.getCharacterByUserId(userId);
        return doAddItem(character, itemId, quantity);
    }

    public Character doAddItem(Character character, Long itemId, Integer quantity) {

        Item item = itemService.getItemById(itemId);

        ItemInstance instance;
        switch (item.getType()) {
            case WEAPON -> {
                if (quantity != null) {
                    throw new GameException("error.game.weapon.quantityNotAllowed", HttpStatus.BAD_REQUEST);
                }
                Weapon weapon = (Weapon) item;
                instance = weaponHandler.createInstance(weapon);
            }
            case ARMOR -> {
                if (quantity != null) {
                    throw new GameException("error.game.armor.quantityNotAllowed", HttpStatus.BAD_REQUEST);
                }
                Armor armor = (Armor) item;
                instance = armorHandler.createInstance(armor);
            }
            case AMMO -> {
                Ammo ammo = (Ammo) item;
                AmmoInstance ammoInstance = ammoHandler.createInstance(ammo);
                ammoInstance.setQuantity(quantity != null ? quantity : 1);
                instance = ammoInstance;
            }
            default -> throw new GameException("error.game.item.invalidType", HttpStatus.BAD_REQUEST);
        }

        character.getInventory().getItems().add(instance);

        return characterService.save(character);
    }

    @Transactional
    public Character equipItem(Long characterId, Long itemInstanceId, EquippedSlot slot) {
        Character character = characterService.getCharacterById(characterId);
        return doEquip(character, itemInstanceId, slot);
    }

    @Transactional
    public Character equipItem(String userId, Long itemInstanceId, EquippedSlot slot) {
        Character character = characterService.getCharacterByUserId(userId);
        return doEquip(character, itemInstanceId, slot);
    }

    private Character doEquip(Character character, Long itemInstanceId, EquippedSlot slot) {
        ItemInstance instance = findItemInstance(itemInstanceId);

        if (instance instanceof WeaponInstance weaponInstance) {
            weaponHandler.equip(character, weaponInstance, slot);
        } else if (instance instanceof ArmorInstance armorInstance) {
            armorHandler.equip(character, armorInstance, slot);
        } else {
            throw new GameException("error.game.item.cannotEquip", HttpStatus.BAD_REQUEST);
        }

        return characterService.save(character);
    }

    @Transactional
    public Character unequipItem(Long characterId, Long itemInstanceId) {
        Character character = characterService.getCharacterById(characterId);
        return doUnequip(character, itemInstanceId);
    }

    @Transactional
    public Character unequipItem(String userId, Long itemInstanceId) {
        Character character = characterService.getCharacterByUserId(userId);
        return doUnequip(character, itemInstanceId);
    }

    public Character doUnequip(Character character, Long itemInstanceId) {

        ItemInstance instance = findItemInstance(itemInstanceId);

        if (instance instanceof WeaponInstance weaponInstance) {
            weaponHandler.unequip(character, weaponInstance);
        } else if (instance instanceof ArmorInstance armorInstance) {
            armorHandler.unequip(character, armorInstance);
        } else {
            throw new GameException("error.game.item.cannotUnequip", HttpStatus.BAD_REQUEST);
        }

        return characterService.save(character);
    }

    @Transactional
    public Character reloadWeapon(Long characterId, Long itemInstanceId, Long ammoInstanceId) {
        Character character = characterService.getCharacterById(characterId);
        return doReload(character, itemInstanceId, ammoInstanceId);
    }

    @Transactional
    public Character reloadWeapon(String userId, Long itemInstanceId, Long ammoInstanceId) {
        Character character = characterService.getCharacterByUserId(userId);
        return doReload(character, itemInstanceId, ammoInstanceId);
    }

    public Character doReload(Character character, Long weaponInstanceId, Long ammoInstanceId) {

        WeaponInstance weapon = (WeaponInstance) findItemInstance(weaponInstanceId);
        AmmoInstance ammo = (AmmoInstance) findItemInstance(ammoInstanceId);

        ammoHandler.reloadWeapon(character, weapon, ammo);

        return characterService.save(character);
    }

    @Transactional
    public Character unloadWeapon(Long characterId, Long weaponInstanceId) {
        Character character = characterService.getCharacterById(characterId);
        return doUnload(character, weaponInstanceId);
    }

    @Transactional
    public Character unloadWeapon(String userId, Long weaponInstanceId) {
        Character character = characterService.getCharacterByUserId(userId);
        return doUnload(character, weaponInstanceId);
    }

    public Character doUnload(Character character, Long weaponInstanceId) {
        WeaponInstance weapon = (WeaponInstance) findItemInstance(weaponInstanceId);

        ammoHandler.unloadWeapon(character, weapon);

        return characterService.save(character);
    }

    @Transactional
    public Character removeItem(Long characterId, Long itemInstanceId) {
        Character character = characterService.getCharacterById(characterId);
        return doRemove(character, itemInstanceId);
    }

    @Transactional
    public Character removeItem(String userId, Long itemInstanceId) {
        Character character = characterService.getCharacterByUserId(userId);
        return doRemove(character, itemInstanceId);
    }

    public Character doRemove(Character character, Long itemInstanceId) {

        ItemInstance itemToRemove = findItemInstance(itemInstanceId);
        character.getInventory().getItems().remove(itemToRemove);

        return characterService.save(character);
    }

    private ItemInstance findItemInstance(Long itemInstanceId) {
        return itemInstanceRepository.findById(itemInstanceId).orElseThrow(() -> new GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Character changeWeaponMode(Long characterId, Long itemInstanceId, Long ammoInstanceId) {
        Character character = characterService.getCharacterById(characterId);
        return doChangeWeaponMode(character, itemInstanceId, ammoInstanceId);
    }

    @Transactional
    public Character changeWeaponMode(String userId, Long itemInstanceId, Long ammoInstanceId) {
        Character character = characterService.getCharacterByUserId(userId);
        return doChangeWeaponMode(character, itemInstanceId, ammoInstanceId);
    }

    public Character doChangeWeaponMode(Character character, Long weaponInstanceId, Long weaponModeId) {
        WeaponInstance weaponInstance = (WeaponInstance) findItemInstance(weaponInstanceId);
        WeaponMode weaponMode = weaponModeService.getWeaponModeById(weaponModeId);

        weaponHandler.changeWeaponMode(weaponInstance, weaponMode);

        return character;
    }
}