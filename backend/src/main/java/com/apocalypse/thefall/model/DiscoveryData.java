package com.apocalypse.thefall.model;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.Tile;

import java.util.List;
import java.util.Set;

public record DiscoveryData(
        Character character,
        Set<Long> discoveredTileIds,
        List<Tile> allTiles,
        List<Tile> toDiscover
) {
}