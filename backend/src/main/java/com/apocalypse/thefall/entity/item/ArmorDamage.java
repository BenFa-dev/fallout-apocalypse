package com.apocalypse.thefall.entity.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "armor_damage",
        uniqueConstraints = @UniqueConstraint(columnNames = {"armor_id", "damage_type_id"}))
@SuperBuilder
@NoArgsConstructor
public class ArmorDamage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armor_id", nullable = false)
    private Armor armor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_type_id", nullable = false)
    private DamageType damageType;

    @Column(name = "threshold", nullable = false)
    private Integer threshold;

    @Column(name = "resistance", nullable = false)
    private Integer resistance;
}