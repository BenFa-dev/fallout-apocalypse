package com.apocalypse.thefall.controller.inventory;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.mapper.inventory.InventoryMapper;
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
    private final InventoryMapper inventoryMapper;
    private final GameProperties gameProperties;

    @GetMapping("/{characterId}")
    public InventoryDto getInventory(@PathVariable Long characterId) {
        return inventoryMapper.toDto(inventoryService.findByCharacterIdFetchItems(characterId), gameProperties);
    }

    @PostMapping("/{characterId}/items/{itemId}")
    public InventoryDto addItem(
            @PathVariable Long characterId,
            @PathVariable Long itemId,
            @RequestParam(required = false) Integer quantity) {
        return inventoryMapper.toDto(inventoryService.addItem(characterId, itemId, quantity), gameProperties);
    }

    @DeleteMapping("/{characterId}/items/{itemInstanceId}")
    public InventoryDto removeItem(@PathVariable Long characterId, @PathVariable Long itemInstanceId) {
        return inventoryMapper.toDto(inventoryService.removeItemFromInventory(characterId, itemInstanceId),
                gameProperties);
    }
}