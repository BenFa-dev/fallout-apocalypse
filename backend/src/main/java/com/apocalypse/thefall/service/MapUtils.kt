package com.apocalypse.thefall.service;

import com.apocalypse.thefall.entity.Tile;

public class MapUtils {

    public static boolean isInVision(Tile tile, int cx, int cy, int range) {
        int dx = Math.abs(tile.getX() - cx);
        int dy = Math.abs(tile.getY() - cy);

        // Chaque déplacement diagonal coûte 2, chaque droit 1
        int diagonalSteps = Math.min(dx, dy);
        int straightSteps = Math.abs(dx - dy);

        int visionCost = (diagonalSteps * 2) + straightSteps;

        return visionCost <= range;
    }
}
