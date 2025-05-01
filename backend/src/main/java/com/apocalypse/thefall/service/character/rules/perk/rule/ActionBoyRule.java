package com.apocalypse.thefall.service.character.rules.perk.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.entity.character.stats.PerkEnum;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.perk.PerkCode;
import com.apocalypse.thefall.service.character.rules.perk.PerkRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@PerkCode(PerkEnum.ACTION_BOY)
@Component
@RequiredArgsConstructor
public class ActionBoyRule implements PerkRule {

    @Override
    public boolean apply(Perk perk, Character character, Map<SpecialEnum, Integer> specialMap, Map<SkillEnum, Integer> skillValues, Integer rank) {
        int agility = specialMap.getOrDefault(SpecialEnum.AGILITY, 0);
        int level = character.getCurrentStats().getLevel();
        return agility >= 5 && level >= 12 && rank < 3;
    }

}

