package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.item.Ammo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "weapon_instance")
@DiscriminatorValue("WEAPON")
public class WeaponInstance extends ItemInstance {

    @ManyToOne
    @JoinColumn(name = "current_ammo_type_id")
    private Ammo currentAmmoType;

    @Column(name = "current_ammo_quantity")
    private Integer currentAmmoQuantity;

    @Column(name = "is_equipped")
    private Boolean isEquipped;
}