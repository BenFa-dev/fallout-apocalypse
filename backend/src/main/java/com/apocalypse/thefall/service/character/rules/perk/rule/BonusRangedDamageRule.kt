package com.apocalypse.thefall.service.character.rules.perk.rule;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.entity.character.stats.enums.PerkEnum;
import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.service.character.rules.perk.PerkCode;
import com.apocalypse.thefall.service.character.rules.perk.PerkRule;
import org.springframework.stereotype.Component;

import java.util.Map;

@PerkCode(PerkEnum.BONUS_RANGED_DAMAGE)
@Component
public class BonusRangedDamageRule implements PerkRule {

    @Override
    public boolean apply(Perk perk, Character character, Map<SpecialEnum, Integer> specialMap, Map<SkillEnum, Integer> skillValues, Integer rank) {
        return character.getCurrentStats().getLevel() >= 6
                && specialMap.getOrDefault(SpecialEnum.AGILITY, 0) >= 6
                && specialMap.getOrDefault(SpecialEnum.LUCK, 0) >= 6
                && rank < 2;
    }
}
