package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.entity.common.BaseNamedEntity
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "special")
open class Special : BaseNamedEntity() {
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    open var code: SpecialEnum? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "short_names", columnDefinition = "jsonb", nullable = false)
    open var shortNames: MutableMap<String, String>? = mutableMapOf()
}
