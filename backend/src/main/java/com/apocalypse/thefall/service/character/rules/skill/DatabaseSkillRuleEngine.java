package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.Expression;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DatabaseSkillRuleEngine implements CharacterSkillRule {

    private final RuleEngine ruleEngine;

    @Override
    public int apply(SkillEnum skillCode, Map<SpecialEnum, Integer> specialValues) {
        Expression expr = ruleEngine.getCompiledExpression(skillCode);
        specialValues.forEach((key, val) -> expr.setVariable(key.name(), val));
        return (int) expr.evaluate();
    }
}
