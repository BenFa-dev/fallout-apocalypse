package com.apocalypse.thefall.service.stats.rule;



import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.service.stats.CharacterStatRule;
import com.apocalypse.thefall.service.stats.CharacterStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HitPointsRule implements CharacterStatRule {

    @Override
    public void apply(Character character, CharacterStats.CharacterStatsBuilder builder) {
        builder.hitPoints(15
                + character.getSpecial().getStrength()
                + (2 * character.getSpecial().getEndurance()));
    }
}
