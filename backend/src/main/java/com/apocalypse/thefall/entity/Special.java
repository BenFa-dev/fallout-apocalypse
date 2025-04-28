package com.apocalypse.thefall.entity;

import com.apocalypse.thefall.entity.character.Character;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "character")
@Table(name = "special")
public class Special {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int strength;
    private int perception;
    private int endurance;
    private int charisma;
    private int intelligence;
    private int agility;
    private int luck;

    @OneToOne(mappedBy = "special")
    private Character character;

    public int calculateDiscovery() {
        return Math.max(1, (int) Math.floor(perception / 2.5));
    }
}