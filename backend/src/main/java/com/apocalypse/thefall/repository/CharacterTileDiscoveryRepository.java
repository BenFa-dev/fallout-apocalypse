package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.entity.CharacterTileDiscovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterTileDiscoveryRepository extends JpaRepository<CharacterTileDiscovery, Long> {

    List<CharacterTileDiscovery> findByCharacterId(Long characterId);
}
