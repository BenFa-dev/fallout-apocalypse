package com.apocalypse.thefall.entity.character

import com.apocalypse.thefall.entity.GameMap
import com.apocalypse.thefall.entity.character.stats.*
import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.entity.inventory.Inventory
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode


@Entity
@Table(name = "character")
open class Character : BaseEntity() {

    @Column(name = "name")
    open var name: String? = null

    // Keycloak user id
    @Column(name = "user_id")
    open var userId: String? = null

    @Column(name = "current_x", nullable = false)
    open var currentX: Int = 0

    @Column(name = "current_y", nullable = false)
    open var currentY: Int = 0

    @Embedded
    open lateinit var currentStats: CharacterCurrentStats

    @get:JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_map_id")
    open var currentMap: GameMap? = null

    @OneToOne(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var inventory: Inventory? = null

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    open val skills: MutableSet<SkillInstance> = mutableSetOf()

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    open val perks: MutableSet<PerkInstance> = mutableSetOf()

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    open var specials: MutableSet<SpecialInstance> = mutableSetOf()

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    open val conditions: MutableSet<ConditionInstance> = mutableSetOf()

    @field:Transient
    open var derivedStats: MutableSet<DerivedStatInstance> = mutableSetOf()

}
