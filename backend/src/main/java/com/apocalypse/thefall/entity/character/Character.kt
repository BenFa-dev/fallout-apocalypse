package com.apocalypse.thefall.entity.character

import com.apocalypse.thefall.entity.GameMap
import com.apocalypse.thefall.entity.character.stats.*
import com.apocalypse.thefall.entity.common.BaseEntity
import com.apocalypse.thefall.entity.inventory.Inventory
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "character")
class Character : BaseEntity() {

    @Column(name = "name")
    var name: String? = null

    // Keycloak user id
    @Column(name = "user_id")
    var userId: String? = null

    @Column(name = "current_x", nullable = false)
    var currentX: Int = 0

    @Column(name = "current_y", nullable = false)
    var currentY: Int = 0

    @Embedded
    var currentStats: CharacterCurrentStats? = null

    @get:JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_map_id")
    var currentMap: GameMap? = null

    @OneToOne(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true)
    var inventory: Inventory? = null

    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val skills: MutableSet<SkillInstance> = mutableSetOf()

    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val perks: MutableSet<PerkInstance> = mutableSetOf()

    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val specials: MutableSet<SpecialInstance> = mutableSetOf()

    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val conditions: MutableSet<ConditionInstance> = mutableSetOf()

    @Transient
    var derivedStats: MutableSet<DerivedStatInstance> = mutableSetOf()
}
