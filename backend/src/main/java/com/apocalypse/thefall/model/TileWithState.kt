package com.apocalypse.thefall.model

import com.apocalypse.thefall.entity.Tile

@JvmRecord
data class TileWithState(val tile: Tile?, val revealed: Boolean, val visible: Boolean)
