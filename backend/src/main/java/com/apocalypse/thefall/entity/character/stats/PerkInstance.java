package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "perk_instance", uniqueConstraints = @UniqueConstraint(columnNames = {"character_id", "perk_id"}))
public class PerkInstance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perk_id", nullable = false)
    private Perk perk;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean active = false;
}
