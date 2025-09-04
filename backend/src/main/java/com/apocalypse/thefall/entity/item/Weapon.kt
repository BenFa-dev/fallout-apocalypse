package com.apocalypse.thefall.entity.item

import com.apocalypse.thefall.entity.item.enums.WeaponType
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "weapon")
@DiscriminatorValue("WEAPON")
open class Weapon : Item() {

    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type", nullable = false)
    open var weaponType: WeaponType? = null

    @Column(name = "required_strength", nullable = false)
    open var requiredStrength: Int = 0

    @Column(name = "required_hands", nullable = false)
    open var requiredHands: Int = 1

    @Column(name = "capacity")
    open var capacity: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "damage_type_id", nullable = false)
    open var damageType: DamageType? = null

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany
    @JoinTable(
        name = "weapon_compatible_ammo",
        joinColumns = [JoinColumn(name = "weapon_id")],
        inverseJoinColumns = [JoinColumn(name = "ammo_id")]
    )
    open var compatibleAmmo: MutableSet<Ammo> = mutableSetOf()

    // TODO fetch eager, we can’t do multiple LEFT JOIN FETCH TREAT(it AS Weapon).xxx in the repository
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "weapon", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    open var modes: MutableSet<WeaponMode> = mutableSetOf()
}
