package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.item.Ammo;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "weapon_instance")
@DiscriminatorValue("WEAPON")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WeaponInstance extends ItemInstance {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_ammo_type_id")
    private Ammo currentAmmoType;

    @Column(name = "current_ammo_quantity")
    @Builder.Default
    private Integer currentAmmoQuantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipped_slot")
    @Builder.Default
    private EquippedSlot equippedSlot = null;
}