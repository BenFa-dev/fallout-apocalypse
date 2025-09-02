package com.apocalypse.thefall.service.character.rules;

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.service.character.rules.util.FormulaUtils;
import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.Expression;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FormulaEngine {

    private final RuleEngine ruleEngine;

    public <T extends Enum<T>, I extends CalculatedInstance<T>> void compute(
            Collection<I> instances,
            Map<SpecialEnum, Integer> specialValues,
            Map<EquippedSlot, ItemInstance> equippedItems
    ) {
        for (I instance : instances) {
            T code = instance.getCode();
            Expression expr = ruleEngine.get(code.name());
            Set<String> variableNames = expr.getVariableNames();

            // Inject variables in formula
            FormulaUtils.injectVariables(expr, variableNames, specialValues, equippedItems);

            int total = instance.getValue() + (int) expr.evaluate();
            instance.setCalculatedValue(total);
        }

    }
}