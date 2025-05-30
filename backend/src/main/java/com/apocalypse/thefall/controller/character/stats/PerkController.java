package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.mapper.character.stats.PerkMapper;
import com.apocalypse.thefall.service.character.CharacterService;
import com.apocalypse.thefall.service.character.stats.PerkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/perks")
@RequiredArgsConstructor
public class PerkController {

    private final CharacterService characterService;
    private final PerkMapper perkMapper;
    private final PerkService perkService;

    @GetMapping("/all")
    public List<DataItemDto> getAll() {
        return perkMapper.toDto(perkService.findAll());
    }

    @GetMapping("/all-available")
    public List<DataItemDto> getAllAvailable(@AuthenticationPrincipal Jwt jwt) {
        return perkMapper.toDto(perkService.findAvailablePerks(characterService.getCharacterByUserId(jwt.getSubject())));
    }
}