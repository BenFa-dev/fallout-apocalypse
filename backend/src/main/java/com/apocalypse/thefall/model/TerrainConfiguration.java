package com.apocalypse.thefall.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "terrain_configuration")
public class TerrainConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> descriptions;

    @Column(nullable = false)
    private int movementCost;

    @Column(nullable = false)
    private boolean walkable = true;

    @Column(name = "svg_path", nullable = false)
    private String svgPath;
}