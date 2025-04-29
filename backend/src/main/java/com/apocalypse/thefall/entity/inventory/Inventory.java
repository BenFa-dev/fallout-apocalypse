package com.apocalypse.thefall.entity.inventory;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.common.BaseEntity;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "inventory")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @OneToMany(mappedBy = "inventory")
    @Builder.Default
    private Set<ItemInstance> items = new HashSet<>();

    public double getCurrentWeight() {
        return items.stream()
                .mapToDouble(item -> item.getItem().getWeight())
                .sum();
    }
}