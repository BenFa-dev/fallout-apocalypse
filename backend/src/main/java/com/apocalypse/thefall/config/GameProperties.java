package com.apocalypse.thefall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game")
@Data
public class GameProperties {
    private InventoryProperties inventory;

    @Data
    public static class InventoryProperties {
        private int baseWeightCapacity = 150;
        private int weightPerStrength = 10;
        private ActionPoints actionPoints;

        @Data
        public static class ActionPoints {
            private int equip = 1;
            private int unequip = 1;
            private int reload = 2;
            private int unload = 1;
        }
    }
}