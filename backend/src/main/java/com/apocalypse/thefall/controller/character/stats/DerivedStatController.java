package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.DerivedStatDto;
import com.apocalypse.thefall.mapper.character.stats.DerivedStatMapper;
import com.apocalypse.thefall.service.character.stats.DerivedStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/derived-stats")
@RequiredArgsConstructor
public class DerivedStatController {

    private final DerivedStatService derivedStatService;
    private final DerivedStatMapper derivedStatMapper;

    @GetMapping("/all")
    public List<DerivedStatDto> getAll() {
        return derivedStatMapper.toDto(derivedStatService.findAll());
    }
}