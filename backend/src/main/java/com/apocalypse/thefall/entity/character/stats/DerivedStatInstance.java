package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.character.stats.enums.DerivedStatEnum;
import com.apocalypse.thefall.service.character.rules.CalculatedInstance;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DerivedStatInstance implements CalculatedInstance<DerivedStatEnum> {

    private DerivedStat derivedStat;

    private Integer value = 0;

    @Transient
    private Integer calculatedValue;

    @Override
    public DerivedStatEnum getCode() {
        return derivedStat.getCode();
    }
}
