package com.apocalypse.thefall.entity.inventory;

import com.apocalypse.thefall.entity.BaseEntity;
import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "inventory")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @OneToMany(mappedBy = "inventory")
    @Builder.Default
    private Set<ItemInstance> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "equipped_armor_id")
    private ArmorInstance equippedArmor;

    @OneToOne
    @JoinColumn(name = "equipped_weapon_primary_id")
    private WeaponInstance equippedWeaponPrimary;

    @OneToOne
    @JoinColumn(name = "equipped_weapon_secondary_id")
    private WeaponInstance equippedWeaponSecondary;
}