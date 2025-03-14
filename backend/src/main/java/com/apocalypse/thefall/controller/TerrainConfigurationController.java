package com.apocalypse.thefall.controller;

import com.apocalypse.thefall.dto.TerrainConfigurationDto;
import com.apocalypse.thefall.mapper.TerrainConfigurationMapper;
import com.apocalypse.thefall.service.TerrainConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/terrain-configurations")
@RequiredArgsConstructor
public class TerrainConfigurationController {
    private final TerrainConfigurationService terrainConfigurationService;
    private final TerrainConfigurationMapper terrainConfigurationMapper;

    @GetMapping
    public List<TerrainConfigurationDto> getAllTerrainConfigurations() {
        return terrainConfigurationMapper.toDtoList(terrainConfigurationService.getAllTerrainConfigurations());
    }
}