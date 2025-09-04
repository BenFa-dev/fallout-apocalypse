package com.apocalypse.thefall.entity.item

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "armor")
@DiscriminatorValue("ARMOR")
open class Armor : Item() {

    @Column(name = "armor_class", nullable = false)
    open var armorClass: Int = 0

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @OneToMany(mappedBy = "armor", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var damages: MutableSet<ArmorDamage> = mutableSetOf()
}
