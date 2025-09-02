package com.apocalypse.thefall.event;

import lombok.Data;

@Data
public class GameEvent {
    private GameEventType type;

    public static GameEvent of(GameEventType type) {
        GameEvent event = new GameEvent();
        event.setType(type);
        return event;
    }
}