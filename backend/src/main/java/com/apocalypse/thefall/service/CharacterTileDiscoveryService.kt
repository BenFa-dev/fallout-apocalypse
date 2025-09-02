package com.apocalypse.thefall.service;

import com.apocalypse.thefall.entity.CharacterTileDiscovery;
import com.apocalypse.thefall.entity.Tile;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.repository.CharacterTileDiscoveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterTileDiscoveryService {
    private final CharacterTileDiscoveryRepository discoveryRepository;

    public void updateTilesDiscovery(Character character, List<Tile> newlyVisibleTiles) {
        List<CharacterTileDiscovery> newDiscoveries = newlyVisibleTiles.stream()
                .map(tile -> CharacterTileDiscovery.builder()
                        .character(character)
                        .tile(tile)
                        .build())
                .toList();

        discoveryRepository.saveAll(newDiscoveries);
    }

    public Set<Long> getDiscoveredTileIds(Long characterId) {
        return discoveryRepository.findByCharacterId(characterId).stream()
                .map(d -> d.getTile().getId())
                .collect(Collectors.toSet());
    }
}
