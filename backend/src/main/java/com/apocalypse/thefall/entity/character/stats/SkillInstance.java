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
@Table(name = "skill_instance", uniqueConstraints = @UniqueConstraint(columnNames = {"character_id", "skill_id"}))
public class SkillInstance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "is_tagged", nullable = false)
    @Builder.Default
    private boolean tagged = false;
}
