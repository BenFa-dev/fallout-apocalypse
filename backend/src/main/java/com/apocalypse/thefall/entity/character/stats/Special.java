package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.common.BaseNamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "special")
public class Special extends BaseNamedEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    private SpecialEnum code;

    @Column(name = "short_names", columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> shortNames;
}
