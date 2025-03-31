package com.apocalypse.thefall.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "terrain_configuration")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "path", nullable = false)
    private String path;
}