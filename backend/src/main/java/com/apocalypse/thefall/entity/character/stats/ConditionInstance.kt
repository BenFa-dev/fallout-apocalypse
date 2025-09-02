package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "condition_instance", uniqueConstraints = @UniqueConstraint(columnNames = {"character_id", "condition_id"}))
public class ConditionInstance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    private Condition condition;

    @Column(name = "value", nullable = false)
    private boolean value;
}