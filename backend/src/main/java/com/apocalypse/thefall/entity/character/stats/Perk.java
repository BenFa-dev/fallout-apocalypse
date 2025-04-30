package com.apocalypse.thefall.entity.character.stats;

import com.apocalypse.thefall.entity.common.BaseNamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "perk")
public class Perk extends BaseNamedEntity {

}
