package com.apocalypse.thefall.entity.instance;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "ammo_instance")
@DiscriminatorValue("AMMO")
@NoArgsConstructor
@SuperBuilder
public class AmmoInstance extends ItemInstance {

    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 0;
}