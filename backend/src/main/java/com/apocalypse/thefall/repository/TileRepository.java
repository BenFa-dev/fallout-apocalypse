package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.entity.Map;
import com.apocalypse.thefall.entity.Tile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TileRepository extends JpaRepository<Tile, Long> {

    Optional<Tile> findByMapAndXAndY(Map map, int x, int y);

    List<Tile> findAllByMapId(Long mapId);

}