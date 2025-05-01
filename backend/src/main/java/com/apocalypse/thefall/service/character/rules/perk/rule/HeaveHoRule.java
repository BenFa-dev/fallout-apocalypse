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

@PerkCode(PerkEnum.HEAVE_HO)
@Component
public class HeaveHoRule implements PerkRule {
    @Override
    public boolean apply(Perk perk, Character character, Map<SpecialEnum, Integer> specialMap, Map<SkillEnum, Integer> skillValues, Integer rank) {
        return character.getCurrentStats().getLevel() >= 6 && rank < 3;
    }
}
