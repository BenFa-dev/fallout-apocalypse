package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "armor_instance")
@DiscriminatorValue("ARMOR")
public class ArmorInstance extends ItemInstance {

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Column(name = "is_equipped")
    private Boolean isEquipped;
}