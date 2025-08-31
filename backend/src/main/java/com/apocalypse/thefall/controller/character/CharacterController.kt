package com.apocalypse.thefall.controller.character

import com.apocalypse.thefall.dto.MoveRequestDto
import com.apocalypse.thefall.dto.character.CharacterCreationDto
import com.apocalypse.thefall.dto.character.CharacterDto
import com.apocalypse.thefall.mapper.character.CharacterMapper
import com.apocalypse.thefall.service.character.CharacterService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/characters")
class CharacterController(
    private val characterService: CharacterService,
    private val characterMapper: CharacterMapper
) {

    @PostMapping
    fun createCharacter(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: CharacterCreationDto
    ): CharacterDto =
        characterMapper.toDto(characterService.createCharacter(jwt.subject, characterMapper.fromCreationDto(request)))

    @GetMapping("/current")
    fun getCurrentCharacter(@AuthenticationPrincipal jwt: Jwt): CharacterDto =
        characterMapper.toDto(characterService.getSimpleCharacterByUserId(jwt.subject))

    @PostMapping("/move")
    fun moveCharacter(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: MoveRequestDto
    ): CharacterDto = characterMapper.toDto(characterService.moveCharacter(jwt.subject, request.x, request.y))
}