package com.apocalypse.thefall.service.character.rules.perk.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.entity.character.stats.PerkEnum;
import com.apocalypse.thefall.entity.character.stats.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.perk.PerkCode;
import com.apocalypse.thefall.service.character.rules.perk.PerkRule;
import org.springframework.stereotype.Component;

import java.util.Map;

@PerkCode(PerkEnum.ANIMAL_FRIEND)
@Component
public class AnimalFriendRule implements PerkRule {

    @Override
    public boolean apply(Perk perk, Character character, Map<SpecialEnum, Integer> specialMap, Map<SkillEnum, Integer> skillValues, Integer rank) {
        int intelligence = specialMap.getOrDefault(SpecialEnum.INTELLIGENCE, 0);
        int outdoorsman = skillValues.getOrDefault(SkillEnum.OUTDOORSMAN, 0);
        return character.getCurrentStats().getLevel() >= 9
                && intelligence >= 5
                && outdoorsman >= 25
                && rank < 1;
    }
}

