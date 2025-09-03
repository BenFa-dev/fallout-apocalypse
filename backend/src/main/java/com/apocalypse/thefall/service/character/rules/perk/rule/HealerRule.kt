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

@PerkCode(PerkEnum.HEALER)
@Component
public class HealerRule implements PerkRule {
    @Override
    public boolean apply(Perk perk, Character character, Map<SpecialEnum, Integer> specialMap, Map<SkillEnum, Integer> skillValues, Integer rank) {
        return character.getCurrentStats().getLevel() >= 3 &&
                specialMap.getOrDefault(SpecialEnum.PERCEPTION, 0) >= 7 &&
                specialMap.getOrDefault(SpecialEnum.INTELLIGENCE, 0) >= 5 &&
                specialMap.getOrDefault(SpecialEnum.AGILITY, 0) >= 6 &&
                rank < 3 &&
                character.getSkills().stream().anyMatch(skill ->
                        skill.getSkill().getCode() == SkillEnum.FIRST_AID && skill.getCalculatedValue() >= 40);
    }
}
