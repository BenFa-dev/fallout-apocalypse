package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.PerkDto;
import com.apocalypse.thefall.mapper.character.stats.PerkMapper;
import com.apocalypse.thefall.service.character.stats.PerkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/perks")
@RequiredArgsConstructor
public class PerkController {

    private final PerkService perkService;
    private final PerkMapper perkMapper;

    @GetMapping("/all")
    public List<PerkDto> getAll() {
        return perkMapper.toDto(perkService.findAll());
    }
}