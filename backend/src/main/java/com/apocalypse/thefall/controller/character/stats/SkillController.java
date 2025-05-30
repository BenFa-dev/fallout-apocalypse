package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.mapper.character.stats.SkillMapper;
import com.apocalypse.thefall.service.character.stats.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    @GetMapping("/all")
    public List<DataItemDto> getAll() {
        return skillMapper.toDto(skillService.findAll());
    }
}