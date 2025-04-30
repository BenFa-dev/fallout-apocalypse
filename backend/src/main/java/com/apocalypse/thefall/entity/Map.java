package com.apocalypse.thefall.entity;

import com.apocalypse.thefall.entity.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"tiles"})
@Table(name = "map")
public class Map extends BaseEntity {
    private String name;
    private int width = 10;
    private int height = 10;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tile> tiles = new HashSet<>();
}