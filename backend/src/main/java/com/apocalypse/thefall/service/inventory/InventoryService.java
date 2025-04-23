package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.*;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.CharacterRepository;
import com.apocalypse.thefall.repository.inventory.InventoryRepository;
import com.apocalypse.thefall.repository.item.ItemRepository;
import com.apocalypse.thefall.repository.iteminstance.ItemInstanceRepository;
import com.apocalypse.thefall.repository.iteminstance.WeaponModeRepository;
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

    private final WeaponHandler weaponHandler;
    private final ArmorHandler armorHandler;
    private final AmmoHandler ammoHandler;
    private final CharacterRepository characterRepository;
    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;
    private final ItemInstanceRepository itemInstanceRepository;
    private final WeaponModeRepository weaponModeRepository;

    @Transactional(readOnly = true)
    public Inventory findByCharacterIdFetchItems(Long characterId) {
        return inventoryRepository.findByCharacterId(characterId).orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Inventory addItem(Long characterId, Long itemId, Integer quantity) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new GameException("error.game.item.notFound", HttpStatus.NOT_FOUND));

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
        characterRepository.save(character);
        return findByCharacterIdFetchItems(characterId);
    }

    @Transactional
    public Inventory equipItem(Long characterId, Long itemInstanceId, EquippedSlot slot) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        ItemInstance instance = findItemInstance(itemInstanceId);

        if (instance instanceof WeaponInstance weaponInstance) {
            weaponHandler.equip(character, weaponInstance, slot);
        } else if (instance instanceof ArmorInstance armorInstance) {
            armorHandler.equip(character, armorInstance, slot);
        } else {
            throw new GameException("error.game.item.cannotEquip", HttpStatus.BAD_REQUEST);
        }

        characterRepository.save(character);

        return findByCharacterIdFetchItems(characterId);
    }

    @Transactional
    public Inventory unequipItem(Long characterId, Long itemInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        ItemInstance instance = findItemInstance(itemInstanceId);

        if (instance instanceof WeaponInstance weaponInstance) {
            weaponHandler.unequip(character, weaponInstance);
        } else if (instance instanceof ArmorInstance armorInstance) {
            armorHandler.unequip(character, armorInstance);
        } else {
            throw new GameException("error.game.item.cannotUnequip", HttpStatus.BAD_REQUEST);
        }

        characterRepository.save(character);

        return findByCharacterIdFetchItems(characterId);
    }

    @Transactional
    public Inventory reloadWeapon(Long characterId, Long weaponInstanceId, Long ammoInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        WeaponInstance weapon = (WeaponInstance) findItemInstance(weaponInstanceId);
        AmmoInstance ammo = (AmmoInstance) findItemInstance(ammoInstanceId);

        ammoHandler.reloadWeapon(character, weapon, ammo);
        characterRepository.save(character);

        return findByCharacterIdFetchItems(characterId);
    }

    @Transactional
    public Inventory unloadWeapon(Long characterId, Long weaponInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        WeaponInstance weapon = (WeaponInstance) findItemInstance(weaponInstanceId);

        ammoHandler.unloadWeapon(character, weapon);
        characterRepository.save(character);

        return findByCharacterIdFetchItems(characterId);
    }

    @Transactional
    public Inventory removeItemFromInventory(Long characterId, Long itemInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        ItemInstance itemToRemove = findItemInstance(itemInstanceId);
        character.getInventory().getItems().remove(itemToRemove);

        characterRepository.save(character);

        return findByCharacterIdFetchItems(characterId);
    }

    private ItemInstance findItemInstance(Long itemInstanceId) {
        return itemInstanceRepository.findById(itemInstanceId).orElseThrow(() -> new GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Inventory changeWeaponMode(Long weaponInstanceId, Long weaponModeId) {
        WeaponInstance weaponInstance = (WeaponInstance) findItemInstance(weaponInstanceId);
        WeaponMode weaponMode = weaponModeRepository.findById(weaponModeId).orElseThrow(() -> new GameException("error.game.inventory.weaponModeNotFound", HttpStatus.NOT_FOUND));

        weaponHandler.changeWeaponMode(weaponInstance, weaponMode);

        return findByCharacterIdFetchItems(weaponInstance.getInventory().getCharacter().getId());
    }
}