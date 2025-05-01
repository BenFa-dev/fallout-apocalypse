package com.apocalypse.thefall.entity.item;

import com.apocalypse.thefall.entity.common.BaseNamedEntity;
import com.apocalypse.thefall.entity.item.enums.DamageTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "damage_type")
@SuperBuilder
@NoArgsConstructor
public class DamageType extends BaseNamedEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    private DamageTypeEnum code;

}
