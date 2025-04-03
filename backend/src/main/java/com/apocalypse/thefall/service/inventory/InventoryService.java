package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Ammo;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.Weapon;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.CharacterRepository;
import com.apocalypse.thefall.repository.item.ItemRepository;
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
    private final ItemRepository itemRepository;

    public Inventory getInventoryByCharacterId(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));
        return character.getInventory();
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
        return character.getInventory();
    }

    @Transactional
    public Inventory equipItem(Long characterId, Long itemInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        ItemInstance instance = findItemInstance(character.getInventory(), itemInstanceId);

        if (instance instanceof WeaponInstance weaponInstance) {
            weaponHandler.equip(character, weaponInstance);
        } else if (instance instanceof ArmorInstance armorInstance) {
            armorHandler.equip(character, armorInstance);
        } else {
            throw new GameException("error.game.item.cannotEquip", HttpStatus.BAD_REQUEST);
        }

        characterRepository.save(character);
        return character.getInventory();
    }

    @Transactional
    public Inventory unequipItem(Long characterId, Long itemInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        ItemInstance instance = findItemInstance(character.getInventory(), itemInstanceId);

        if (instance instanceof WeaponInstance weaponInstance) {
            weaponHandler.unequip(character, weaponInstance);
        } else if (instance instanceof ArmorInstance armorInstance) {
            armorHandler.unequip(character, armorInstance);
        } else {
            throw new GameException("error.game.item.cannotUnequip", HttpStatus.BAD_REQUEST);
        }

        characterRepository.save(character);
        return character.getInventory();
    }

    @Transactional
    public Inventory reloadWeapon(Long characterId, Long weaponInstanceId, Long ammoInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        WeaponInstance weapon = (WeaponInstance) findItemInstance(character.getInventory(), weaponInstanceId);
        AmmoInstance ammo = (AmmoInstance) findItemInstance(character.getInventory(), ammoInstanceId);

        ammoHandler.reloadWeapon(character, weapon, ammo);
        characterRepository.save(character);
        return character.getInventory();
    }

    @Transactional
    public Inventory unloadWeapon(Long characterId, Long weaponInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        WeaponInstance weapon = (WeaponInstance) findItemInstance(character.getInventory(), weaponInstanceId);

        ammoHandler.unloadWeapon(character, weapon);
        characterRepository.save(character);
        return character.getInventory();
    }

    @Transactional
    public Inventory removeItemFromInventory(Long characterId, Long itemInstanceId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));

        ItemInstance itemToRemove = findItemInstance(character.getInventory(), itemInstanceId);
        character.getInventory().getItems().remove(itemToRemove);

        characterRepository.save(character);
        return character.getInventory();
    }

    private ItemInstance findItemInstance(Inventory inventory, Long itemInstanceId) {
        return inventory.getItems().stream()
                .filter(item -> item.getId().equals(itemInstanceId))
                .findFirst()
                .orElseThrow(() -> new GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND));
    }
}