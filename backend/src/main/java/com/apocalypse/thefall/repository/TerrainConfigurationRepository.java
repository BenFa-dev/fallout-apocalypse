package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.model.TerrainConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerrainConfigurationRepository extends JpaRepository<TerrainConfiguration, Long> {
    Optional<TerrainConfiguration> findByName(String name);
}