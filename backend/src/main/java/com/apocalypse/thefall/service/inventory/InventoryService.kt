package com.apocalypse.thefall.service.inventory

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.instance.AmmoInstance
import com.apocalypse.thefall.entity.instance.ArmorInstance
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.instance.WeaponInstance
import com.apocalypse.thefall.entity.item.Ammo
import com.apocalypse.thefall.entity.item.Armor
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.Weapon
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.iteminstance.ItemInstanceRepository
import com.apocalypse.thefall.service.character.CharacterService
import com.apocalypse.thefall.service.inventory.handler.AmmoHandler
import com.apocalypse.thefall.service.inventory.handler.ArmorHandler
import com.apocalypse.thefall.service.inventory.handler.WeaponHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class InventoryService(
    private val ammoHandler: AmmoHandler,
    private val armorHandler: ArmorHandler,
    private val characterService: CharacterService,
    private val itemInstanceRepository: ItemInstanceRepository,
    private val itemService: ItemService,
    private val weaponHandler: WeaponHandler,
    private val weaponModeService: WeaponModeService
) {

    @Transactional(readOnly = true)
    open fun get(characterId: Long): Character = characterService.getCharacterById(characterId)

    @Transactional(readOnly = true)
    open fun get(userId: String): Character = characterService.getCharacterByUserId(userId)

    @Transactional
    open fun addItem(characterId: Long, itemId: Long, quantity: Int?): Character {
        val character = characterService.getCharacterById(characterId)
        return doAddItem(character, itemId, quantity)
    }

    @Transactional
    open fun addItem(userId: String, itemId: Long, quantity: Int?): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doAddItem(character, itemId, quantity)
    }

    fun doAddItem(character: Character, itemId: Long, quantity: Int?): Character {
        val instance: ItemInstance = when (val item: Item = itemService.getItemById(itemId)) {
            is Weapon -> {
                if (quantity != null) {
                    throw GameException("error.game.weapon.quantityNotAllowed", HttpStatus.BAD_REQUEST)
                }
                weaponHandler.createInstance(item)
            }

            is Armor -> {
                if (quantity != null) {
                    throw GameException("error.game.armor.quantityNotAllowed", HttpStatus.BAD_REQUEST)
                }
                armorHandler.createInstance(item)
            }

            is Ammo -> {
                val ammoInstance = ammoHandler.createInstance(item)
                ammoInstance.quantity = quantity ?: 1
                ammoInstance
            }

            else -> throw GameException("error.game.item.invalidType", HttpStatus.BAD_REQUEST)
        }

        character.inventory!!.items.add(instance)

        return characterService.save(character)
    }

    @Transactional
    open fun equipItem(characterId: Long, itemInstanceId: Long, slot: EquippedSlot): Character {
        val character = characterService.getCharacterById(characterId)
        return doEquip(character, itemInstanceId, slot)
    }

    @Transactional
    open fun equipItem(userId: String, itemInstanceId: Long, slot: EquippedSlot): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doEquip(character, itemInstanceId, slot)
    }

    private fun doEquip(character: Character, itemInstanceId: Long, slot: EquippedSlot): Character {
        when (val instance = findItemInstance(itemInstanceId)) {
            is WeaponInstance -> weaponHandler.equip(character, instance, slot)
            is ArmorInstance -> armorHandler.equip(character, instance, slot)
            else -> throw GameException("error.game.item.cannotEquip", HttpStatus.BAD_REQUEST)
        }

        return characterService.save(character)
    }

    @Transactional
    open fun unequipItem(characterId: Long, itemInstanceId: Long): Character {
        val character = characterService.getCharacterById(characterId)
        return doUnequip(character, itemInstanceId)
    }

    @Transactional
    open fun unequipItem(userId: String, itemInstanceId: Long): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doUnequip(character, itemInstanceId)
    }

    fun doUnequip(character: Character, itemInstanceId: Long): Character {
        when (val instance = findItemInstance(itemInstanceId)) {
            is WeaponInstance -> weaponHandler.unequip(character, instance)
            is ArmorInstance -> armorHandler.unequip(character, instance)
            else -> throw GameException("error.game.item.cannotUnequip", HttpStatus.BAD_REQUEST)
        }

        return characterService.save(character)
    }

    @Transactional
    open fun reloadWeapon(characterId: Long, itemInstanceId: Long, ammoInstanceId: Long): Character {
        val character = characterService.getCharacterById(characterId)
        return doReload(character, itemInstanceId, ammoInstanceId)
    }

    @Transactional
    open fun reloadWeapon(userId: String, itemInstanceId: Long, ammoInstanceId: Long): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doReload(character, itemInstanceId, ammoInstanceId)
    }

    fun doReload(character: Character, weaponInstanceId: Long, ammoInstanceId: Long): Character {
        val weapon = findItemInstance(weaponInstanceId) as? WeaponInstance
            ?: throw GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND)
        val ammo = findItemInstance(ammoInstanceId) as? AmmoInstance
            ?: throw GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND)

        ammoHandler.reloadWeapon(character, weapon, ammo)

        return characterService.save(character)
    }

    @Transactional
    open fun unloadWeapon(characterId: Long, weaponInstanceId: Long): Character {
        val character = characterService.getCharacterById(characterId)
        return doUnload(character, weaponInstanceId)
    }

    @Transactional
    open fun unloadWeapon(userId: String, weaponInstanceId: Long): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doUnload(character, weaponInstanceId)
    }

    fun doUnload(character: Character, weaponInstanceId: Long): Character {
        val weapon = findItemInstance(weaponInstanceId) as? WeaponInstance
            ?: throw GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND)

        ammoHandler.unloadWeapon(character, weapon)

        return characterService.save(character)
    }

    @Transactional
    open fun removeItem(characterId: Long, itemInstanceId: Long): Character {
        val character = characterService.getCharacterById(characterId)
        return doRemove(character, itemInstanceId)
    }

    @Transactional
    open fun removeItem(userId: String, itemInstanceId: Long): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doRemove(character, itemInstanceId)
    }

    fun doRemove(character: Character, itemInstanceId: Long): Character {
        val itemToRemove = findItemInstance(itemInstanceId)
        character.inventory!!.items.remove(itemToRemove)
        return characterService.save(character)
    }

    private fun findItemInstance(itemInstanceId: Long): ItemInstance =
        itemInstanceRepository.findById(itemInstanceId)
            .orElseThrow { GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND) }

    @Transactional
    open fun changeWeaponMode(characterId: Long, itemInstanceId: Long, weaponModeId: Long): Character {
        val character = characterService.getCharacterById(characterId)
        return doChangeWeaponMode(character, itemInstanceId, weaponModeId)
    }

    @Transactional
    open fun changeWeaponMode(userId: String, itemInstanceId: Long, weaponModeId: Long): Character {
        val character = characterService.getCharacterByUserId(userId)
        return doChangeWeaponMode(character, itemInstanceId, weaponModeId)
    }

    fun doChangeWeaponMode(character: Character, weaponInstanceId: Long, weaponModeId: Long): Character {
        val weaponInstance = findItemInstance(weaponInstanceId) as? WeaponInstance
            ?: throw GameException("error.game.inventory.itemNotFound", HttpStatus.NOT_FOUND)
        val weaponMode = weaponModeService.getWeaponModeById(weaponModeId)

        weaponHandler.changeWeaponMode(weaponInstance, weaponMode)

        return character
    }
}
