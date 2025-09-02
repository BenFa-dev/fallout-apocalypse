package com.apocalypse.thefall.service;

import com.apocalypse.thefall.entity.TerrainConfiguration;
import com.apocalypse.thefall.repository.TerrainConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerrainConfigurationService {
    private final TerrainConfigurationRepository terrainConfigurationRepository;

    @Transactional(readOnly = true)
    public List<TerrainConfiguration> getAllTerrainConfigurations() {
        return terrainConfigurationRepository.findAll();
    }
}