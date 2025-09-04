package com.apocalypse.thefall.entity.item

import com.apocalypse.thefall.entity.common.BaseNamedEntity
import com.apocalypse.thefall.entity.item.enums.DamageTypeEnum
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable

@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "damage_type")
open class DamageType : BaseNamedEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    open lateinit var code: DamageTypeEnum
}
