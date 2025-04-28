package com.apocalypse.thefall.entity;

import com.apocalypse.thefall.entity.character.Character;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "character_tile_discovery")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterTileDiscovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_id", nullable = false)
    private Tile tile;
}
