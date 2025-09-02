package com.apocalypse.thefall.event;

import lombok.Data;

@Data
public class CharacterMovementEvent {
    private String userId;
    private Long characterId;
    private int newX;
    private int newY;
    private int remainingActionPoints;
    private long timestamp;

    public static CharacterMovementEvent of(
            String userId,
            Long characterId,
            int newX,
            int newY,
            int remainingActionPoints
    ) {
        CharacterMovementEvent event = new CharacterMovementEvent();
        event.setUserId(userId);
        event.setCharacterId(characterId);
        event.setNewX(newX);
        event.setNewY(newY);
        event.setRemainingActionPoints(remainingActionPoints);
        event.setTimestamp(System.currentTimeMillis());
        return event;
    }
}