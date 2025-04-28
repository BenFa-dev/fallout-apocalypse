package com.apocalypse.thefall.controller.skill;

import com.apocalypse.thefall.dto.character.skill.SkillDto;
import com.apocalypse.thefall.mapper.SkillMapper;
import com.apocalypse.thefall.service.skill.SkillService;
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
    public List<SkillDto> getAll() {
        return skillMapper.toDto(skillService.findAll());
    }
}