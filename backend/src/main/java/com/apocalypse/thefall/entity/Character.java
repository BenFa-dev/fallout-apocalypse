package com.apocalypse.thefall.entity;

import com.apocalypse.thefall.entity.inventory.Inventory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(exclude = {"currentMap", "special"})
@Table(name = "character")
@SuperBuilder
@NoArgsConstructor
public class Character extends BaseEntity {

    private String name;

    @Column(name = "user_id")
    private String userId; // ID Keycloak de l'utilisateur

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "special_id")
    private Special special;

    @Column(name = "current_x")
    private int currentX;

    @Column(name = "current_y")
    private int currentY;

    @Column(name = "action_points")
    private int actionPoints;

    @Column(name = "hit_points")
    private int hitPoints;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "current_map_id")
    private Map currentMap;

    @OneToOne(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;
}