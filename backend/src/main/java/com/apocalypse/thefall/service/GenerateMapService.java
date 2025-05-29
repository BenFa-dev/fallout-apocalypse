package com.apocalypse.thefall.service;

import com.apocalypse.thefall.entity.GameMap;
import com.apocalypse.thefall.entity.TerrainConfiguration;
import com.apocalypse.thefall.entity.Tile;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.MapRepository;
import com.apocalypse.thefall.repository.TerrainConfigurationRepository;
import com.apocalypse.thefall.repository.TileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateMapService {
    private final MapRepository mapRepository;
    private final TileRepository tileRepository;
    private final TerrainConfigurationRepository terrainConfigurationRepository;
    private final Random random = new Random();

    @Transactional
    public GameMap getOrCreateMap() {
        return mapRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> generateMap(15, 15));
    }

    @Transactional
    public GameMap generateMap(int width, int height) {
        List<TerrainConfiguration> terrainTypes = terrainConfigurationRepository.findAll();
        if (terrainTypes.isEmpty()) {
            throw new GameException("error.game.configuration.missing", HttpStatus.INTERNAL_SERVER_ERROR, "terrain");
        }

        GameMap map = new GameMap();
        map.setName("Wasteland");
        map.setWidth(width);
        map.setHeight(height);
        map = mapRepository.save(map);

        // Générer les tuiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = new Tile();
                tile.setMap(map);
                tile.setX(x);
                tile.setY(y);
                tile.setTerrainConfiguration(terrainTypes.get(random.nextInt(terrainTypes.size())));
                tileRepository.save(tile);
            }
        }

        return map;
    }
}