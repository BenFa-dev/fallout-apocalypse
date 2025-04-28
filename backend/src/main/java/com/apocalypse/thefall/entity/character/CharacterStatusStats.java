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
public class CharacterStatusStats {

    @Column(name = "is_poisoned")
    private boolean poisoned;

    @Column(name = "is_radiated")
    private boolean radiated;

    @Column(name = "has_eye_damage")
    private boolean eyeDamage;

    @Column(name = "is_right_arm_crippled")
    private boolean rightArmCrippled;

    @Column(name = "is_left_arm_crippled")
    private boolean leftArmCrippled;

    @Column(name = "is_right_leg_crippled")
    private boolean rightLegCrippled;

    @Column(name = "is_left_leg_crippled")
    private boolean leftLegCrippled;
}
