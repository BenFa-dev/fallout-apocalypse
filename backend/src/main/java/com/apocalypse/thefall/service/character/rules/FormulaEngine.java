package com.apocalypse.thefall.service.character.rules;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.Expression;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FormulaEngine {

    private final RuleEngine ruleEngine;
    private final SpecialService specialService;

    public <T extends Enum<T>, I extends CalculatedInstance<T>> void compute(
            Collection<I> instances, Character character
    ) {
        Map<SpecialEnum, Integer> specialValues = specialService.getSpecialValuesMap(character);
        for (I instance : instances) {
            T code = instance.getCode();
            Expression expr = ruleEngine.get(code.name());

            specialValues.forEach((k, v) -> expr.setVariable(k.name(), v));

            int total = instance.getValue() + (int) expr.evaluate();
            instance.setCalculatedValue(total);
        }

    }
}