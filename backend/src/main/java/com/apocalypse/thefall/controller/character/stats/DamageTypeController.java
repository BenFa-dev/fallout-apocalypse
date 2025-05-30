package com.apocalypse.thefall.controller.character.stats;

import com.apocalypse.thefall.dto.character.stats.DataItemDto;
import com.apocalypse.thefall.mapper.character.stats.DamageTypeMapper;
import com.apocalypse.thefall.service.inventory.DamageTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/damage-types")
@RequiredArgsConstructor
public class DamageTypeController {

    private final DamageTypeService damageTypeService;
    private final DamageTypeMapper damageTypeMapper;

    @GetMapping("/all")
    public List<DataItemDto> getAll() {
        return damageTypeMapper.toDto(damageTypeService.findAll());
    }
}