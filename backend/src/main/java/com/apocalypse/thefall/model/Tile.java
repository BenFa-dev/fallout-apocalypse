package com.apocalypse.thefall.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = { "map", "terrainConfiguration" })
@Table(name = "tile")
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
}