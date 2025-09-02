package com.apocalypse.thefall.entity.item

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "armor")
@DiscriminatorValue("ARMOR")
open class Armor : Item() {

    @Column(name = "armor_class", nullable = false)
    open var armorClass: Int = 0

    @JsonManagedReference
    @OneToMany(mappedBy = "armor", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var damages: MutableSet<ArmorDamage> = mutableSetOf()
}
