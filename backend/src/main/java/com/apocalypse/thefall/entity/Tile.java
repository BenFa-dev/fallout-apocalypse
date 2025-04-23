package com.apocalypse.thefall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;

@Entity
@Getter
@Setter
@ToString(exclude = {"map", "terrainConfiguration"})
@Table(name = "tile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    private int x;
    private int y;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "terrain_configuration_id", nullable = false)
    private TerrainConfiguration terrainConfiguration;

    public boolean isWalkable() {
        return terrainConfiguration.isWalkable();
    }

    public int getMovementCost() {
        return terrainConfiguration.getMovementCost();
    }

    public static Tile unknownAt(int x, int y, Map map) {
        return Tile.builder()
                .x(x)
                .y(y)
                .map(map)
                .terrainConfiguration(TerrainConfiguration.builder()
                        .name("unknown")
                        .walkable(false)
                        .movementCost(999)
                        .descriptions(Collections.emptyMap())
                        .build())
                .build();
    }

}