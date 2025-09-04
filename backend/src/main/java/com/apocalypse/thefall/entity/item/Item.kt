package com.apocalypse.thefall.entity.item

import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.entity.item.enums.ItemType
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
abstract class Item : BaseEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    open var type: ItemType? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    open var names: MutableMap<String, String> = mutableMapOf()

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    open var descriptions: MutableMap<String, String> = mutableMapOf()

    @Column(nullable = false)
    open var weight: Double = 0.0

    @Column(name = "base_price", nullable = false)
    open var basePrice: Int = 0

    @Column(name = "path", nullable = false)
    open var path: String? = null
}
