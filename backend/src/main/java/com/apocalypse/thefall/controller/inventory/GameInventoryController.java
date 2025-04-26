package com.apocalypse.thefall.controller.inventory;

import com.apocalypse.thefall.dto.CharacterDto;
import com.apocalypse.thefall.mapper.CharacterMapper;
import com.apocalypse.thefall.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/inventory")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class GameInventoryController {

    private final InventoryService inventoryService;
    private final CharacterMapper characterMapper;

    @PostMapping("/{characterId}/items/{itemId}")
    public CharacterDto addItem(
            @PathVariable Long characterId,
            @PathVariable Long itemId,
            @RequestParam(required = false) Integer quantity) {
        return characterMapper.toDto(inventoryService.addItem(characterId, itemId, quantity));
    }

    @DeleteMapping("/{characterId}/items/{itemInstanceId}")
    public CharacterDto removeItem(@PathVariable Long characterId, @PathVariable Long itemInstanceId) {
        return characterMapper.toDto(inventoryService.removeItem(characterId, itemInstanceId));
    }
}