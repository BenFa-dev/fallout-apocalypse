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
@Table(name = "armor")
@DiscriminatorValue("ARMOR")
public class Armor extends Item {
    @Column(name = "armor_class", nullable = false)
    private Integer armorClass;

    @Column(name = "damage_threshold_normal", nullable = false)
    private Integer damageThresholdNormal;
 
    @Column(name = "damage_threshold_laser", nullable = false)
    private Integer damageThresholdLaser;

    @Column(name = "damage_threshold_fire", nullable = false)
    private Integer damageThresholdFire;

    @Column(name = "damage_threshold_plasma", nullable = false)
    private Integer damageThresholdPlasma;

    @Column(name = "damage_threshold_explosive", nullable = false)
    private Integer damageThresholdExplosive;

    @Column(name = "damage_threshold_electric", nullable = false)
    private Integer damageThresholdElectric;

    @Column(name = "damage_resistance_normal", nullable = false)
    private Integer damageResistanceNormal;

    @Column(name = "damage_resistance_laser", nullable = false)
    private Integer damageResistanceLaser;

    @Column(name = "damage_resistance_fire", nullable = false)
    private Integer damageResistanceFire;

    @Column(name = "damage_resistance_plasma", nullable = false)
    private Integer damageResistancePlasma;

    @Column(name = "damage_resistance_explosive", nullable = false)
    private Integer damageResistanceExplosive;

    @Column(name = "damage_resistance_electric", nullable = false)
    private Integer damageResistanceElectric;
}