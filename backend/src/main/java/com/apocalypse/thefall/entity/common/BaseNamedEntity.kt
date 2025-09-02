package com.apocalypse.thefall.entity.common


import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@MappedSuperclass
open class BaseNamedEntity : BaseEntity() {

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "names", columnDefinition = "jsonb", nullable = false)
    open var names: MutableMap<String, String> = mutableMapOf()

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "descriptions", columnDefinition = "jsonb", nullable = false)
    open var descriptions: MutableMap<String, String> = mutableMapOf()

    @Column(name = "image_path")
    open var imagePath: String? = null

    @Column(name = "display_order", nullable = false)
    open var displayOrder: Int = 0

    @Column(name = "visible", nullable = false)
    open var visible: Boolean = true
}

