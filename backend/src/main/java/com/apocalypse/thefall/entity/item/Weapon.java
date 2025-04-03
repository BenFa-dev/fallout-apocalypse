package com.apocalypse.thefall.entity.item;

import com.apocalypse.thefall.entity.item.enums.DamageType;
import com.apocalypse.thefall.entity.item.enums.WeaponType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "weapon")
@DiscriminatorValue("WEAPON")
public class Weapon extends Item {
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type", nullable = false)
    private WeaponType weaponType;

    @Column(name = "required_strength", nullable = false)
    private Integer requiredStrength;

    @Column(name = "required_hands", nullable = false)
    private Integer requiredHands;

    @Column(name = "capacity")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "damage_type", nullable = false)
    private DamageType damageType = DamageType.NORMAL;

    @ManyToMany
    @JoinTable(name = "weapon_compatible_ammo", joinColumns = @JoinColumn(name = "weapon_id"), inverseJoinColumns = @JoinColumn(name = "ammo_id"))
    private Set<Ammo> compatibleAmmo = new HashSet<>();

    @OneToMany(mappedBy = "weapon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WeaponMode> modes = new HashSet<>();
}