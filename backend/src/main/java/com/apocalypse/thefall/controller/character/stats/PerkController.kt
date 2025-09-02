package com.apocalypse.thefall.controller.character.stats

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.mapper.character.stats.perk.toListDataItemDto
import com.apocalypse.thefall.service.character.CharacterService
import com.apocalypse.thefall.service.character.stats.PerkService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/perks")
class PerkController(
    private val characterService: CharacterService,
    private val perkService: PerkService
) {

    @GetMapping("/all")
    fun getAll(): List<DataItemDto> = perkService.findAll().toListDataItemDto()

    @GetMapping("/all-available")
    fun getAllAvailable(@AuthenticationPrincipal jwt: Jwt): List<DataItemDto> =
        perkService.findAvailablePerks(characterService.getCharacterByUserId(jwt.subject)).toListDataItemDto()
}
