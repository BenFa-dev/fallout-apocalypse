package com.apocalypse.thefall.event

data class CharacterMovementEvent(
    var userId: String? = null,
    var characterId: Long? = null,
    var newX: Int = 0,
    var newY: Int = 0,
    var remainingActionPoints: Int = 0,
    var timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        fun of(
            userId: String,
            characterId: Long?,
            newX: Int,
            newY: Int,
            remainingActionPoints: Int
        ): CharacterMovementEvent {
            return CharacterMovementEvent(
                userId = userId,
                characterId = characterId,
                newX = newX,
                newY = newY,
                remainingActionPoints = remainingActionPoints,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}
