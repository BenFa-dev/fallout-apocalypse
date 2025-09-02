package com.apocalypse.thefall.controller.inventory

import com.apocalypse.thefall.dto.character.CharacterInventoryDto
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.mapper.character.toCharacterInventoryDto
import com.apocalypse.thefall.service.inventory.InventoryService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/player/inventory")
open class PlayerInventoryController(
    private val inventoryService: InventoryService,
) {

    @GetMapping
    @PreAuthorize("hasRole('player') ")
    open fun getMyInventory(@AuthenticationPrincipal jwt: Jwt): CharacterInventoryDto =
        inventoryService.get(jwt.subject).toCharacterInventoryDto()

    @PostMapping("/equip/{itemInstanceId}")
    @PreAuthorize("hasRole('player')")
    open fun equipItem(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable itemInstanceId: Long,
        @RequestParam slot: EquippedSlot
    ): CharacterInventoryDto {
        return inventoryService.equipItem(
            jwt.subject,
            itemInstanceId,
            slot
        ).toCharacterInventoryDto()
    }

    @PostMapping("/unequip/{itemInstanceId}")
    @PreAuthorize("hasRole('player')")
    open fun unequipItem(@AuthenticationPrincipal jwt: Jwt, @PathVariable itemInstanceId: Long): CharacterInventoryDto {
        return inventoryService.unequipItem(jwt.subject, itemInstanceId).toCharacterInventoryDto()
    }

    @PostMapping("/reload/{weaponInstanceId}/{ammoInstanceId}")
    @PreAuthorize("hasRole('player')")
    open fun reloadWeapon(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable weaponInstanceId: Long,
        @PathVariable ammoInstanceId: Long
    ): CharacterInventoryDto {
        return inventoryService.reloadWeapon(
            jwt.subject,
            weaponInstanceId,
            ammoInstanceId
        ).toCharacterInventoryDto()

    }

    @PostMapping("/unload/{weaponInstanceId}")
    @PreAuthorize("hasRole('player')")
    open fun unloadWeapon(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable weaponInstanceId: Long
    ): CharacterInventoryDto {
        return inventoryService.unloadWeapon(
            jwt.subject,
            weaponInstanceId
        ).toCharacterInventoryDto()
    }

    @PostMapping("/weapon-mode/{weaponInstanceId}/{weaponModeId}")
    @PreAuthorize("hasRole('player')")
    open fun changeWeaponMode(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable weaponInstanceId: Long,
        @PathVariable weaponModeId: Long
    ): CharacterInventoryDto {
        return inventoryService.changeWeaponMode(
            jwt.subject,
            weaponInstanceId,
            weaponModeId
        ).toCharacterInventoryDto()
    }
}