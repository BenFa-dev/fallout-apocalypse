package com.apocalypse.thefall.entity.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ammo")
@DiscriminatorValue("AMMO")
public class Ammo extends Item {
    @Column(name = "armor_class_modifier", nullable = false)
    private Integer armorClassModifier;

    @Column(name = "damage_resistance_modifier", nullable = false)
    private Integer damageResistanceModifier;
 
    @Column(name = "damage_modifier", nullable = false)
    private Integer damageModifier;

    @Column(name = "damage_threshold_modifier", nullable = false)
    private Integer damageThresholdModifier;
}