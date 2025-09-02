package com.apocalypse.thefall.controller.inventory

import com.apocalypse.thefall.dto.character.CharacterDto
import com.apocalypse.thefall.mapper.character.toDto
import com.apocalypse.thefall.service.inventory.InventoryService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/inventory")
@PreAuthorize("hasRole('ADMIN')")
open class GameInventoryController(
    private val inventoryService: InventoryService,
) {
    @PostMapping("/{characterId}/items/{itemId}")
    open fun addItem(
        @PathVariable characterId: Long,
        @PathVariable itemId: Long,
        @RequestParam(required = false) quantity: Int?
    ): CharacterDto {
        return inventoryService.addItem(characterId, itemId, quantity).toDto()
    }

    @DeleteMapping("/{characterId}/items/{itemInstanceId}")
    open fun removeItem(@PathVariable characterId: Long, @PathVariable itemInstanceId: Long): CharacterDto {
        return inventoryService.removeItem(characterId, itemInstanceId).toDto()
    }
}