package com.apocalypse.thefall.entity.character;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CharacterCurrentStats {

    @Column(name = "action_points")
    private int actionPoints;

    @Column(name = "hit_points")
    private int hitPoints;

    @Column(name = "level")
    private int level;
}
