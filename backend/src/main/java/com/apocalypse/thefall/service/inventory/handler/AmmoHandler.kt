package com.apocalypse.thefall.service.inventory.handler

import com.apocalypse.thefall.config.GameProperties
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.instance.AmmoInstance
import com.apocalypse.thefall.entity.instance.WeaponInstance
import com.apocalypse.thefall.entity.inventory.Inventory
import com.apocalypse.thefall.entity.item.Ammo
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.Weapon
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.iteminstance.AmmoInstanceRepository
import com.apocalypse.thefall.repository.iteminstance.WeaponInstanceRepository
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory
import org.hibernate.Hibernate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AmmoHandler(
    private val weaponInstanceRepository: WeaponInstanceRepository,
    private val ammoInstanceRepository: AmmoInstanceRepository,
    itemInstanceFactory: ItemInstanceFactory,
    gameProperties: GameProperties
) : AbstractItemHandler<Ammo, AmmoInstance>(itemInstanceFactory, gameProperties) {

    override fun createInstance(item: Ammo): AmmoInstance = itemInstanceFactory.createInstance(item) as AmmoInstance

    override fun validateRequirements(character: Character, item: Ammo) = validateWeight(character, item)

    override fun equip(character: Character, itemInstance: AmmoInstance, slot: EquippedSlot) {
        throw GameException("error.game.ammo.cannotEquip", HttpStatus.BAD_REQUEST)
    }

    override fun unequip(character: Character, itemInstance: AmmoInstance) {
        throw GameException("error.game.ammo.cannotUnequip", HttpStatus.BAD_REQUEST)
    }

    private fun getReloadActionPointsCost(): Int = gameProperties.inventory.actionPoints.reload

    private fun getUnloadActionPointsCost(): Int = gameProperties.inventory.actionPoints.unload

    fun reloadWeapon(character: Character, weaponInstance: WeaponInstance, ammoInstance: AmmoInstance) {
        validateActionPoints(character, getReloadActionPointsCost())

        val weaponItem = Hibernate.unproxy(weaponInstance.item) as Item
        val ammoItem = Hibernate.unproxy(ammoInstance.item) as Item

        if (weaponItem is Weapon && ammoItem is Ammo) {
            if (!weaponItem.compatibleAmmo.contains(ammoItem)) {
                throw GameException("error.game.weapon.incompatibleAmmo", HttpStatus.BAD_REQUEST)
            }

            // Unload if another amm
            if (weaponInstance.currentAmmoType != null &&
                weaponInstance.currentAmmoType?.id != ammoInstance.item?.id
            ) {
                unloadWeapon(character, weaponInstance)
            }

            weaponInstance.currentAmmoType = ammoItem
            val quantityToLoad = minOf(ammoInstance.quantity, weaponItem.capacity ?: 0)
            weaponInstance.currentAmmoQuantity = quantityToLoad
            weaponInstanceRepository.save(weaponInstance)

            val remaining = ammoInstance.quantity - quantityToLoad
            ammoInstance.quantity = remaining
            if (remaining <= 0) {
                ammoInstanceRepository.delete(ammoInstance)
                character.inventory?.items?.remove(ammoInstance)
            } else {
                ammoInstanceRepository.save(ammoInstance)
            }
        }

        consumeActionPoints(character, getReloadActionPointsCost())
    }

    fun unloadWeapon(character: Character, weapon: WeaponInstance) {
        validateActionPoints(character, getUnloadActionPointsCost())

        if (weapon.currentAmmoQuantity <= 0) {
            throw GameException("error.game.weapon.noAmmoToUnload", HttpStatus.BAD_REQUEST)
        }

        val inventory: Inventory = character.inventory
            ?: throw GameException("error.game.inventory.notFound", HttpStatus.INTERNAL_SERVER_ERROR)

        val ammoType = weapon.currentAmmoType
            ?: throw GameException("error.game.weapon.noAmmoToUnload", HttpStatus.BAD_REQUEST)

        val existingInstance = inventory.items
            .asSequence()
            .filterIsInstance<AmmoInstance>()
            .firstOrNull { it.item == ammoType }

        if (existingInstance != null) {
            existingInstance.quantity = existingInstance.quantity + weapon.currentAmmoQuantity
            ammoInstanceRepository.save(existingInstance)
        } else {
            val newInstance = itemInstanceFactory.createInstance(ammoType) as AmmoInstance
            newInstance.quantity = weapon.currentAmmoQuantity
            newInstance.inventory = inventory
            val saved = ammoInstanceRepository.save(newInstance)
            character.inventory?.items?.add(saved)
        }

        weapon.currentAmmoType = null
        weapon.currentAmmoQuantity = 0
        weaponInstanceRepository.save(weapon)

        consumeActionPoints(character, getUnloadActionPointsCost())
    }
}
