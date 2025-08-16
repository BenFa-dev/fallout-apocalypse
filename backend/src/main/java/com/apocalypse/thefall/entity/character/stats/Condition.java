package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.character.stats.enums.ConditionEnum;
import com.apocalypse.thefall.entity.common.BaseNamedEntity;
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
@Table(name = "condition")
public class Condition extends BaseNamedEntity {

    @Column(name = "code", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ConditionEnum code;
    
}

