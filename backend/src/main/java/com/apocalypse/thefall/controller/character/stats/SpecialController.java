package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.SpecialDto;
import com.apocalypse.thefall.mapper.character.stats.SpecialMapper;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specials")
@RequiredArgsConstructor
public class SpecialController {

    private final SpecialService specialService;
    private final SpecialMapper specialMapper;

    @GetMapping("/all")
    public List<SpecialDto> getAll() {
        return specialMapper.toDto(specialService.findAll());
    }
}