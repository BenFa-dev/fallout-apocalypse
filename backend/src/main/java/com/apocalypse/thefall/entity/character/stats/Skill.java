package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum;
import com.apocalypse.thefall.entity.common.BaseNamedEntity;
import com.apocalypse.thefall.service.character.rules.FormulaEntity;
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
@Table(name = "skill")
public class Skill extends BaseNamedEntity implements FormulaEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    private SkillEnum code;

    @Column(name = "formula")
    private String formula;
}
