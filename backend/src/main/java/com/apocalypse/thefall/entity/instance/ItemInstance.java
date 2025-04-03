package com.apocalypse.thefall.entity.instance;

import com.apocalypse.thefall.entity.BaseEntity;
import com.apocalypse.thefall.entity.inventory.Inventory;
import com.apocalypse.thefall.entity.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "item_instance")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "instance_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ItemInstance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
}