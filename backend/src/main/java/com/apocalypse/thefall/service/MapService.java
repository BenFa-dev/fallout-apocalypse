package com.apocalypse.thefall.service;

import com.apocalypse.thefall.entity.Tile;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.model.DiscoveryData;
import com.apocalypse.thefall.model.TileWithState;
import com.apocalypse.thefall.repository.MapRepository;
import com.apocalypse.thefall.repository.TileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MapService {
    private final CharacterService characterService;
    private final CharacterTileDiscoveryService discoveryService;
    private final MapRepository mapRepository;
    private final TileRepository tileRepository;

    @Transactional(readOnly = true)
    public com.apocalypse.thefall.entity.Map getMap(Long mapId) {
        return mapRepository.findById(mapId)
                .orElseThrow(() -> new GameException(
                        "error.resource.notfound",
                        HttpStatus.NOT_FOUND,
                        "Map",
                        "id",
                        String.valueOf(mapId)));
    }

    /**
     * Charge l'état initial de la carte pour le joueur :
     * - Marque les tuiles déjà découvertes
     * - Marque comme visibles celles dans le champ de vision
     * - Masque les tuiles inconnues (non découvertes ni visibles)
     * - Met à jour les nouvelles découvertes si nécessaires
     */
    @Transactional
    public List<TileWithState> getInitialTiles(String userId, Long mapId) {
        DiscoveryData data = updateDiscoveryTiles(userId, mapId);
        int range = data.character().getSpecial().calculateDiscovery();

        return data.allTiles().stream()
                .map(tile -> {
                    boolean revealed = data.discoveredTileIds().contains(tile.getId()) || data.toDiscover().contains(tile);
                    boolean visible = MapUtils.isInVision(tile, data.character().getCurrentX(), data.character().getCurrentY(), range);

                    // Si la tuile est totalement inconnue (hors vision et non découverte), on retourne une tuile "masquée"
                    if (!revealed && !visible) {
                        return new TileWithState(Tile.unknownAt(tile.getX(), tile.getY(), tile.getMap()), false, false);
                    }

                    return new TileWithState(tile, revealed, visible);
                })
                .toList();
    }

    /**
     * Découvre dynamiquement les nouvelles tuiles visibles après un déplacement du joueur.
     * Met à jour les nouvelles découvertes
     */
    @Transactional
    public List<TileWithState> discoverNewVisibleTiles(String userId, Long mapId) {
        DiscoveryData data = updateDiscoveryTiles(userId, mapId);
        Character character = data.character();
        int range = character.getSpecial().calculateDiscovery();

        // Toutes les tuiles actuellement visibles
        List<Tile> currentlyVisible = data.allTiles().stream()
                .filter(tile -> MapUtils.isInVision(tile, character.getCurrentX(), character.getCurrentY(), range))
                .toList();

        // Crée la réponse avec l'état de révélation à jour
        return currentlyVisible.stream()
                .map(tile -> {
                    boolean revealed = data.discoveredTileIds().contains(tile.getId()) || data.toDiscover().contains(tile);
                    return new TileWithState(tile, revealed, true);
                })
                .toList();
    }


    private DiscoveryData updateDiscoveryTiles(String userId, Long mapId) {
        Character character = characterService.getCharacterByUserId(userId);
        Set<Long> discoveredTileIds = discoveryService.getDiscoveredTileIds(character.getId());
        int range = character.getSpecial().calculateDiscovery();

        List<Tile> allTiles = tileRepository.findAllByMapId(mapId);

        // Tuiles visibles et non encore découvertes
        List<Tile> toDiscover = allTiles.stream()
                .filter(tile -> !discoveredTileIds.contains(tile.getId()))
                .filter(tile -> MapUtils.isInVision(tile, character.getCurrentX(), character.getCurrentY(), range))
                .toList();

        discoveryService.updateTilesDiscovery(character, toDiscover);

        return new DiscoveryData(character, discoveredTileIds, allTiles, toDiscover);
    }
}
