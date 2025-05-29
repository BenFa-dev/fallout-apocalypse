package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.ConditionDto;
import com.apocalypse.thefall.mapper.character.stats.ConditionMapper;
import com.apocalypse.thefall.service.character.stats.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conditions")
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;
    private final ConditionMapper conditionMapper;

    @GetMapping("/all")
    public List<ConditionDto> getAll() {
        return conditionMapper.toDto(conditionService.findAll());
    }
}