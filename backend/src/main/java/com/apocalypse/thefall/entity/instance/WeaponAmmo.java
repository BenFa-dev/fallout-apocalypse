package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.BaseEntity;
import com.apocalypse.thefall.entity.item.Ammo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "weapon_instance_ammo")
public class WeaponAmmo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "weapon_instance_id", nullable = false)
    private WeaponInstance weaponInstance;

    @ManyToOne
    @JoinColumn(name = "ammo_id", nullable = false)
    private Ammo ammo;

    @Column(name = "current_ammo", nullable = false)
    private Integer currentAmmo;
}