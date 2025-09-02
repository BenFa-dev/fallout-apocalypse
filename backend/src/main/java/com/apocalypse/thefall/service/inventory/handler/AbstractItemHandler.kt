package com.apocalypse.thefall.service.inventory.handler

import com.apocalypse.thefall.config.GameProperties
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.enums.DerivedStatEnum
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory
import org.springframework.http.HttpStatus

abstract class AbstractItemHandler<T : Item, I : ItemInstance>(
    protected val itemInstanceFactory: ItemInstanceFactory,
    protected val gameProperties: GameProperties
) : ItemHandler<T, I> {

    abstract override fun createInstance(item: T): I
    abstract override fun validateRequirements(character: Character, item: T)
    abstract override fun equip(character: Character, itemInstance: I, slot: EquippedSlot)
    abstract override fun unequip(character: Character, itemInstance: I)

    protected fun validateWeight(character: Character, item: Item) {
        val carryWeight: Int = character.derivedStats
            .firstOrNull { it.code == DerivedStatEnum.CARRY_WEIGHT }
            ?.calculatedValue
            ?: throw GameException(
                "error.game.inventory.noCarryWeight",
                HttpStatus.INTERNAL_SERVER_ERROR
            )

        val currentWeight = character.inventory?.getCurrentWeight() ?: 0.0
        val itemWeight = item.weight
        val totalWeight = currentWeight + itemWeight

        if (totalWeight > carryWeight.toDouble()) {
            throw GameException("error.game.inventory.tooHeavy", HttpStatus.BAD_REQUEST)
        }
    }

    protected fun validateActionPoints(character: Character, requiredActionPoints: Int) {
        val ap = character.currentStats?.actionPoints ?: throw GameException(
            "error.game.character.notEnoughActionPoints",
            HttpStatus.BAD_REQUEST
        )

        if (ap < requiredActionPoints) {
            throw GameException("error.game.character.notEnoughActionPoints", HttpStatus.BAD_REQUEST)
        }
    }

    protected fun consumeActionPoints(character: Character, actionPoints: Int) {
        val stats = character.currentStats ?: throw GameException(
            "error.game.character.notEnoughActionPoints",
            HttpStatus.BAD_REQUEST
        )
        stats.actionPoints -= actionPoints
    }

    protected fun getEquipActionPointsCost(): Int =
        gameProperties.inventory.actionPoints.equip

    protected fun getUnequipActionPointsCost(): Int =
        gameProperties.inventory.actionPoints.unequip
}
