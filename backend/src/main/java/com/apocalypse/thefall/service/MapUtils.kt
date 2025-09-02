package com.apocalypse.thefall.service

import com.apocalypse.thefall.entity.Tile
import kotlin.math.abs
import kotlin.math.min

object MapUtils {

    fun isInVision(tile: Tile, cx: Int, cy: Int, range: Int): Boolean {
        val dx = abs(tile.x - cx)
        val dy = abs(tile.y - cy)

        // Each diagonal move costs 2, each straight move costs 1
        val diagonalSteps = min(dx, dy)
        val straightSteps = abs(dx - dy)

        val visionCost = (diagonalSteps * 2) + straightSteps

        return visionCost <= range
    }
}
