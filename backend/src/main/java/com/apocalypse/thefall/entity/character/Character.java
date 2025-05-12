package com.apocalypse.thefall.entity.character;

import com.apocalypse.thefall.entity.Map;
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance;
import com.apocalypse.thefall.entity.character.stats.PerkInstance;
import com.apocalypse.thefall.entity.character.stats.SkillInstance;
import com.apocalypse.thefall.entity.character.stats.SpecialInstance;
import com.apocalypse.thefall.entity.common.BaseEntity;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"currentMap", "skills", "specials", "perks"})
@Table(name = "character")
@SuperBuilder
@NoArgsConstructor
public class Character extends BaseEntity {

    private String name;

    @Column(name = "user_id")
    private String userId; // ID Keycloak de l'utilisateur

    @Column(name = "current_x")
    private int currentX;

    @Column(name = "current_y")
    private int currentY;

    @Embedded
    private CharacterCurrentStats currentStats;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "current_map_id")
    private Map currentMap;

    @OneToOne(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<SkillInstance> skills = new HashSet<>();

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PerkInstance> perks = new HashSet<>();

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<SpecialInstance> specials = new HashSet<>();

    @Builder.Default
    @Transient
    private Set<DerivedStatInstance> derivedStats = new HashSet<>();

}