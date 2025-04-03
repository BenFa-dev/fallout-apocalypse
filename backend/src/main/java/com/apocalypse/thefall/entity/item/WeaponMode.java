package com.apocalypse.thefall.entity.item;

import com.apocalypse.thefall.entity.BaseEntity;
import com.apocalypse.thefall.entity.item.enums.WeaponModeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "weapon_mode")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class WeaponMode extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_id", nullable = false)
    private Weapon weapon;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_type", nullable = false)
    private WeaponModeType modeType;

    @Column(name = "action_points", nullable = false)
    private Integer actionPoints;

    @Column(name = "min_damage", nullable = false)
    private Integer minDamage;

    @Column(name = "max_damage", nullable = false)
    private Integer maxDamage;

    @Column(name = "range", nullable = false)
    private Integer range;

    @Column(name = "shots_per_burst")
    private Integer shotsPerBurst;
}