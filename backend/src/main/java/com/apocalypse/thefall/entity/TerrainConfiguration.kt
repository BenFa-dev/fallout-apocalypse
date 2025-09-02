package com.apocalypse.thefall.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "terrain_configuration")
open class TerrainConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(unique = true, nullable = false)
    open var name: String = ""

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    open var descriptions: MutableMap<String, String> = mutableMapOf()

    @Column(nullable = false)
    open var movementCost: Int = 0

    @Column(nullable = false)
    open var walkable: Boolean = true

    @Column(name = "path", nullable = false)
    open var path: String = ""
}