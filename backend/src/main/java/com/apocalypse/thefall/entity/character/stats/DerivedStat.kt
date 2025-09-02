package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.character.stats.enums.DerivedStatEnum;
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
@Table(name = "derived_stat")
public class DerivedStat extends BaseNamedEntity implements FormulaEntity {

    @Column(name = "code", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private DerivedStatEnum code;

    @Column(name = "formula")
    private String formula;
}

