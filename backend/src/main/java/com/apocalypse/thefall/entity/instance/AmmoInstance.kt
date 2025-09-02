package com.apocalypse.thefall.entity.instance

// AmmoInstance.kt
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "ammo_instance")
@DiscriminatorValue("AMMO")
open class AmmoInstance : ItemInstance() {

    @Column(name = "quantity", nullable = false)
    open var quantity: Int = 0
}
