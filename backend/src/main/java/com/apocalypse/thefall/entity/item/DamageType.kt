package com.apocalypse.thefall.entity.item

import com.apocalypse.thefall.entity.common.BaseNamedEntity
import com.apocalypse.thefall.entity.item.enums.DamageTypeEnum
import jakarta.persistence.*

@Entity
@Table(name = "damage_type")
open class DamageType : BaseNamedEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    open lateinit var code: DamageTypeEnum
}
