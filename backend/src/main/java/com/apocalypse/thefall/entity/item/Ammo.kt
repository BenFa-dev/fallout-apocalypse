package com.apocalypse.thefall.entity.item

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "ammo")
@DiscriminatorValue("AMMO")
open class Ammo : Item() {

    @Column(name = "armor_class_modifier", nullable = false)
    open var armorClassModifier: Int = 0

    @Column(name = "damage_resistance_modifier", nullable = false)
    open var damageResistanceModifier: Int = 0

    @Column(name = "damage_modifier", nullable = false)
    open var damageModifier: Int = 0

    @Column(name = "damage_threshold_modifier", nullable = false)
    open var damageThresholdModifier: Int = 0
}
