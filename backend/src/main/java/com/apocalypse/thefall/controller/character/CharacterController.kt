package com.apocalypse.thefall.controller.character

import com.apocalypse.thefall.dto.MoveRequestDto
import com.apocalypse.thefall.dto.character.CharacterCreationDto
import com.apocalypse.thefall.dto.character.CharacterDto
import com.apocalypse.thefall.mapper.character.toDto
import com.apocalypse.thefall.mapper.character.toEntity
import com.apocalypse.thefall.service.character.CharacterService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/characters")
class CharacterController(
    private val characterService: CharacterService
) {

    @PostMapping
    fun createCharacter(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: CharacterCreationDto
    ): CharacterDto =
        characterService.createCharacter(jwt.subject, request.toEntity()).toDto()

    @GetMapping("/current")
    fun getCurrentCharacter(@AuthenticationPrincipal jwt: Jwt): CharacterDto =
        characterService.getSimpleCharacterByUserId(jwt.subject).toDto()

    @PostMapping("/move")
    fun moveCharacter(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: MoveRequestDto
    ): CharacterDto = characterService.moveCharacter(jwt.subject, request.x, request.y).toDto()
}