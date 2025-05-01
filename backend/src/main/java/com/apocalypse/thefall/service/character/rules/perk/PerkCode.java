package com.apocalypse.thefall.service.character.rules.perk;

import com.apocalypse.thefall.entity.character.stats.PerkEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PerkCode {
    PerkEnum value();
}
