package com.apocalypse.thefall.controller.inventory;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.dto.inventory.InventoryDto;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.mapper.inventory.InventoryMapper;
import com.apocalypse.thefall.service.CharacterService;
import com.apocalypse.thefall.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player/inventory")
@RequiredArgsConstructor
public class PlayerInventoryController {

    private final InventoryService inventoryService;
    private final CharacterService characterService;
    private final InventoryMapper inventoryMapper;
    private final GameProperties gameProperties;

    @GetMapping
    @PreAuthorize("hasRole('player') ")
    public InventoryDto getMyInventory(@AuthenticationPrincipal Jwt jwt) {
        Character character = characterService.getCharacterByUserId(jwt.getSubject());
        return inventoryMapper.toDto(inventoryService.getInventoryByCharacterId(character.getId()), gameProperties);
    }

    @PostMapping("/equip/{itemInstanceId}")
    @PreAuthorize("hasRole('player')")
    public InventoryDto equipItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemInstanceId) {
        Character character = characterService.getCharacterByUserId(jwt.getSubject());
        return inventoryMapper.toDto(inventoryService.equipItem(character.getId(), itemInstanceId), gameProperties);
    }

    @PostMapping("/unequip/{itemInstanceId}")
    @PreAuthorize("hasRole('player')")
    public InventoryDto unequipItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemInstanceId) {
        Character character = characterService.getCharacterByUserId(jwt.getSubject());
        return inventoryMapper.toDto(inventoryService.unequipItem(character.getId(), itemInstanceId), gameProperties);
    }

    @PostMapping("/reload/{weaponInstanceId}/{ammoInstanceId}")
    @PreAuthorize("hasRole('player')")
    public InventoryDto reloadWeapon(@AuthenticationPrincipal Jwt jwt, @PathVariable Long weaponInstanceId,
                                     @PathVariable Long ammoInstanceId) {
        Character character = characterService.getCharacterByUserId(jwt.getSubject());
        return inventoryMapper.toDto(inventoryService.reloadWeapon(character.getId(), weaponInstanceId, ammoInstanceId),
                gameProperties);
    }

    @PostMapping("/unload/{weaponInstanceId}")
    @PreAuthorize("hasRole('player')")
    public InventoryDto unloadWeapon(@AuthenticationPrincipal Jwt jwt, @PathVariable Long weaponInstanceId) {
        Character character = characterService.getCharacterByUserId(jwt.getSubject());
        return inventoryMapper.toDto(inventoryService.unloadWeapon(character.getId(), weaponInstanceId),
                gameProperties);
    }
}