package com.apocalypse.thefall.entity.item

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable

@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(
    name = "armor_damage",
    uniqueConstraints = [UniqueConstraint(columnNames = ["armor_id", "damage_type_id"])]
)
open class ArmorDamage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armor_id", nullable = false)
    @JsonBackReference
    open var armor: Armor? = null

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_type_id", nullable = false)
    open var damageType: DamageType? = null

    @Column(name = "threshold", nullable = false)
    open var threshold: Int = 0

    @Column(name = "resistance", nullable = false)
    open var resistance: Int = 0
}
