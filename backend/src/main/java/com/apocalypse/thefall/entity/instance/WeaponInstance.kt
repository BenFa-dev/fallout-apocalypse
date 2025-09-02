package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.item.Ammo;
import com.apocalypse.thefall.entity.item.WeaponMode;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity
@Getter
@Setter
@Table(name = "weapon_instance")
@DiscriminatorValue("WEAPON")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WeaponInstance extends ItemInstance {

    @OneToOne
    @JoinColumn(name = "current_ammo_type_id")
    private Ammo currentAmmoType;

    @Column(name = "current_ammo_quantity")
    @Builder.Default
    private Integer currentAmmoQuantity = 0;

    @OneToOne
    @JoinColumn(name = "current_weapon_mode_id")
    private WeaponMode currentWeaponMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipped_slot")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Builder.Default
    private EquippedSlot equippedSlot = null;
}