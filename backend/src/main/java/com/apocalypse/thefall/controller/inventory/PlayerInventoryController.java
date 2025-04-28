package com.apocalypse.thefall.controller.inventory;

import com.apocalypse.thefall.dto.character.CharacterInventoryDto;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.mapper.CharacterMapper;
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
    private final CharacterMapper characterMapper;

    @GetMapping
    @PreAuthorize("hasRole('player') ")
    public CharacterInventoryDto getMyInventory(@AuthenticationPrincipal Jwt jwt) {
        return characterMapper.toCharacterInventoryDto(inventoryService.get(jwt.getSubject()));
    }

    @PostMapping("/equip/{itemInstanceId}")
    @PreAuthorize("hasRole('player')")
    public CharacterInventoryDto equipItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemInstanceId, @RequestParam EquippedSlot slot) {
        return characterMapper.toCharacterInventoryDto(inventoryService.equipItem(jwt.getSubject(), itemInstanceId, slot));
    }

    @PostMapping("/unequip/{itemInstanceId}")
    @PreAuthorize("hasRole('player')")
    public CharacterInventoryDto unequipItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemInstanceId) {
        return characterMapper.toCharacterInventoryDto(inventoryService.unequipItem(jwt.getSubject(), itemInstanceId));
    }

    @PostMapping("/reload/{weaponInstanceId}/{ammoInstanceId}")
    @PreAuthorize("hasRole('player')")
    public CharacterInventoryDto reloadWeapon(@AuthenticationPrincipal Jwt jwt, @PathVariable Long weaponInstanceId,
                                              @PathVariable Long ammoInstanceId) {
        return characterMapper.toCharacterInventoryDto(inventoryService.reloadWeapon(jwt.getSubject(), weaponInstanceId, ammoInstanceId));
    }

    @PostMapping("/unload/{weaponInstanceId}")
    @PreAuthorize("hasRole('player')")
    public CharacterInventoryDto unloadWeapon(@AuthenticationPrincipal Jwt jwt, @PathVariable Long weaponInstanceId) {
        return characterMapper.toCharacterInventoryDto(inventoryService.unloadWeapon(jwt.getSubject(), weaponInstanceId));
    }

    @PostMapping("/weapon-mode/{weaponInstanceId}/{weaponModeId}")
    @PreAuthorize("hasRole('player')")
    public CharacterInventoryDto changeWeaponMode(@AuthenticationPrincipal Jwt jwt, @PathVariable Long weaponInstanceId,
                                                  @PathVariable Long weaponModeId) {
        return characterMapper.toCharacterInventoryDto(inventoryService.changeWeaponMode(jwt.getSubject(), weaponInstanceId, weaponModeId));
    }
}