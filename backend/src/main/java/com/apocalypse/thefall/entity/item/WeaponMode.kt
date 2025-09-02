package com.apocalypse.thefall.entity.item

import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.entity.item.enums.WeaponModeType
import jakarta.persistence.*

@Entity
@Table(name = "weapon_mode")
open class WeaponMode : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_id", nullable = false)
    open var weapon: Weapon? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_type", nullable = false)
    open var modeType: WeaponModeType? = null

    @Column(name = "action_points", nullable = false)
    open var actionPoints: Int = 0

    @Column(name = "min_damage", nullable = false)
    open var minDamage: Int = 0

    @Column(name = "max_damage", nullable = false)
    open var maxDamage: Int = 0

    @Column(name = "range", nullable = false)
    open var range: Int = 0

    @Column(name = "shots_per_burst")
    open var shotsPerBurst: Int? = null
}
