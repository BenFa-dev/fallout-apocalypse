package com.apocalypse.thefall.service.inventory.handler

import com.apocalypse.thefall.config.GameProperties
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.entity.instance.WeaponInstance
import com.apocalypse.thefall.entity.inventory.Inventory
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.Weapon
import com.apocalypse.thefall.entity.item.WeaponMode
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.iteminstance.WeaponInstanceRepository
import com.apocalypse.thefall.service.character.stats.SpecialService
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory
import org.hibernate.Hibernate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
open class WeaponHandler(
    private val weaponInstanceRepository: WeaponInstanceRepository,
    itemInstanceFactory: ItemInstanceFactory,
    gameProperties: GameProperties,
    private val specialService: SpecialService
) : AbstractItemHandler<Weapon, WeaponInstance>(itemInstanceFactory, gameProperties) {

    override fun createInstance(item: Weapon): WeaponInstance =
        itemInstanceFactory.createInstance(item) as WeaponInstance

    override fun validateRequirements(character: Character, item: Weapon) {
        val str = specialService.getSpecialValuesMap(character).getOrDefault(SpecialEnum.STRENGTH, 0)
        if (str < item.requiredStrength) {
            throw GameException(
                "error.game.weapon.notEnoughStrength",
                HttpStatus.BAD_REQUEST,
                item.requiredStrength.toString()
            )
        }
    }

    override fun equip(character: Character, itemInstance: WeaponInstance, slot: EquippedSlot) {
        if (slot == itemInstance.equippedSlot) return

        val item = Hibernate.unproxy(itemInstance.item) as Item
        if (item is Weapon) {
            validateActionPoints(character, getEquipActionPointsCost())
            validateRequirements(character, item)
        }

        val inventory: Inventory? = character.inventory
        val existing: WeaponInstance? = inventory?.items
            ?.filterIsInstance<WeaponInstance>()
            ?.firstOrNull { it.equippedSlot == slot }

        val previous = itemInstance.equippedSlot
        itemInstance.equippedSlot = slot
        if (existing != null) existing.equippedSlot = previous

        if (existing != null) {
            weaponInstanceRepository.saveAll(listOf(itemInstance, existing))
        } else {
            weaponInstanceRepository.save(itemInstance)
        }

        consumeActionPoints(character, getEquipActionPointsCost())
    }

    override fun unequip(character: Character, itemInstance: WeaponInstance) {
        validateActionPoints(character, getUnequipActionPointsCost())

        if (itemInstance.equippedSlot == null) {
            throw GameException("error.game.weapon.notEquipped", HttpStatus.BAD_REQUEST)
        }

        itemInstance.equippedSlot = null
        weaponInstanceRepository.save(itemInstance)

        consumeActionPoints(character, getUnequipActionPointsCost())
    }

    fun changeWeaponMode(weaponInstance: WeaponInstance, weaponMode: WeaponMode) {
        weaponInstance.currentWeaponMode = weaponMode
    }
}
