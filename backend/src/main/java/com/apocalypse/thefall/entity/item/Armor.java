package com.apocalypse.thefall.entity.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "armor")
@DiscriminatorValue("ARMOR")
@SuperBuilder
@NoArgsConstructor
public class Armor extends Item {

    @Column(name = "armor_class", nullable = false)
    private Integer armorClass;

    @OneToMany(mappedBy = "armor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ArmorDamage> damages = new HashSet<>();
}