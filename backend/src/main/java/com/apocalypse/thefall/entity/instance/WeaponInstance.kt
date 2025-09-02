package com.apocalypse.thefall.entity.instance

import com.apocalypse.thefall.entity.item.Ammo
import com.apocalypse.thefall.entity.item.WeaponMode
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType

@Entity
@Table(name = "weapon_instance")
@DiscriminatorValue("WEAPON")
open class WeaponInstance : ItemInstance() {

    @OneToOne
    @JoinColumn(name = "current_ammo_type_id")
    open var currentAmmoType: Ammo? = null

    @Column(name = "current_ammo_quantity")
    open var currentAmmoQuantity: Int = 0

    @OneToOne
    @JoinColumn(name = "current_weapon_mode_id")
    open var currentWeaponMode: WeaponMode? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "equipped_slot")
    @JdbcType(PostgreSQLEnumJdbcType::class)
    open var equippedSlot: EquippedSlot? = null
}