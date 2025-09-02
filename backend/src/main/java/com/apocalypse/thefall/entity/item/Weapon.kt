package com.apocalypse.thefall.entity.item;

import com.apocalypse.thefall.entity.item.enums.WeaponType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "weapon")
@DiscriminatorValue("WEAPON")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "damage_type_id", nullable = false)
    private DamageType damageType;

    @ManyToMany
    @JoinTable(name = "weapon_compatible_ammo", joinColumns = @JoinColumn(name = "weapon_id"), inverseJoinColumns = @JoinColumn(name = "ammo_id"))
    @Builder.Default
    private Set<Ammo> compatibleAmmo = new HashSet<>();

    // TODO fetch eager, on peut pas faire de multiples LEFT JOIN FETCH TREAT(it AS Weapon).xxx dans le repo
    // A revoir...
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "weapon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<WeaponMode> modes = new HashSet<>();
}