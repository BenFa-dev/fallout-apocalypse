package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "armor_instance")
@DiscriminatorValue("ARMOR")
@NoArgsConstructor
@SuperBuilder
public class ArmorInstance extends ItemInstance {
    @Enumerated(EnumType.STRING)
    @Column(name = "equipped_slot")
    @Builder.Default
    private EquippedSlot equippedSlot = null;
}