package com.apocalypse.thefall.entity.instance

// ArmorInstance.kt
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType

@Entity
@Table(name = "armor_instance")
@DiscriminatorValue("ARMOR")
open class ArmorInstance : ItemInstance() {

    @Enumerated(EnumType.STRING)
    @Column(name = "equipped_slot")
    @JdbcType(PostgreSQLEnumJdbcType::class)
    open var equippedSlot: EquippedSlot? = null
}
