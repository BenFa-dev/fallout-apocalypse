package com.apocalypse.thefall.model;

import com.apocalypse.thefall.entity.Tile;

public record TileWithState(Tile tile, boolean revealed, boolean visible) {
}
