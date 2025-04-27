package com.apocalypse.thefall.controller;

import com.apocalypse.thefall.dto.CharacterCreationDto;
import com.apocalypse.thefall.dto.CharacterDto;
import com.apocalypse.thefall.dto.CharacterInventoryDto;
import com.apocalypse.thefall.dto.MoveRequestDto;
import com.apocalypse.thefall.mapper.CharacterMapper;
import com.apocalypse.thefall.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    @PostMapping
    public CharacterDto createCharacter(@AuthenticationPrincipal Jwt jwt, @RequestBody CharacterCreationDto request) {
        return characterMapper.toDto(characterService.createCharacter(jwt.getSubject(), characterMapper.fromCreationDto(request)));
    }

    @GetMapping("/current")
    public CharacterDto getCurrentCharacter(@AuthenticationPrincipal Jwt jwt) {
        return characterMapper.toDto(characterService.getCharacterByUserId(jwt.getSubject()));
    }

    @PostMapping("/move")
    public CharacterInventoryDto moveCharacter(@AuthenticationPrincipal Jwt jwt, @RequestBody MoveRequestDto request) {
        return characterMapper.toCharacterInventoryDto(characterService.moveCharacter(jwt.getSubject(), request.x(), request.y()));
    }
}