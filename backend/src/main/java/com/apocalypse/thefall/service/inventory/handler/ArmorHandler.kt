package com.apocalypse.thefall.service.inventory.handler

import com.apocalypse.thefall.config.GameProperties
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.instance.ArmorInstance
import com.apocalypse.thefall.entity.inventory.Inventory
import com.apocalypse.thefall.entity.item.Armor
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.iteminstance.ArmorInstanceRepository
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory
import org.hibernate.Hibernate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ArmorHandler(
    private val armorInstanceRepository: ArmorInstanceRepository,
    itemInstanceFactory: ItemInstanceFactory,
    gameProperties: GameProperties
) : AbstractItemHandler<Armor, ArmorInstance>(itemInstanceFactory, gameProperties) {

    override fun createInstance(item: Armor): ArmorInstance = itemInstanceFactory.createInstance(item) as ArmorInstance

    override fun validateRequirements(character: Character, item: Armor) {
        validateActionPoints(character, getEquipActionPointsCost())
        validateWeight(character, item)
    }

    override fun equip(character: Character, itemInstance: ArmorInstance, slot: EquippedSlot) {
        if (slot == itemInstance.equippedSlot) return

        validateActionPoints(character, getEquipActionPointsCost())
        val armor = Hibernate.unproxy(itemInstance.item) as Armor
        validateRequirements(character, armor)

        val inventory: Inventory = character.inventory
            ?: throw GameException("error.game.inventory.notFound", HttpStatus.INTERNAL_SERVER_ERROR)

        // Unequipped on this slot
        inventory.items
            .asSequence()
            .filterIsInstance<ArmorInstance>()
            .filter { it.equippedSlot == slot }
            .forEach {
                it.equippedSlot = null
                armorInstanceRepository.save(it)
            }

        // Equip new armor
        itemInstance.equippedSlot = slot
        armorInstanceRepository.save(itemInstance)

        // Consume AP
        consumeActionPoints(character, getEquipActionPointsCost())
    }

    override fun unequip(character: Character, itemInstance: ArmorInstance) {
        validateActionPoints(character, getUnequipActionPointsCost())

        if (itemInstance.equippedSlot != EquippedSlot.ARMOR) {
            throw GameException("error.game.armor.notEquipped", HttpStatus.BAD_REQUEST)
        }

        itemInstance.equippedSlot = null
        armorInstanceRepository.save(itemInstance)

        consumeActionPoints(character, getUnequipActionPointsCost())
    }
}
