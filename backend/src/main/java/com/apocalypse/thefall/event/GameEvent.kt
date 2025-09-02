package com.apocalypse.thefall.event

data class GameEvent(
    var type: GameEventType? = null
) {
    companion object {
        fun of(type: GameEventType): GameEvent {
            return GameEvent(type = type)
        }
    }
}
