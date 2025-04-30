package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.stats.SkillEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkillCode {
    SkillEnum value();
}
