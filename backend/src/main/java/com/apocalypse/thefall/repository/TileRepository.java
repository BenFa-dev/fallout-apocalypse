package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.model.Map;
import com.apocalypse.thefall.model.Tile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TileRepository extends JpaRepository<Tile, Long> {
    List<Tile> findByMapId(Long mapId);

    Optional<Tile> findByMapAndXAndY(Map map, int x, int y);
}