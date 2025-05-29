package com.apocalypse.thefall.service.character.rules;

import com.apocalypse.thefall.service.character.rules.util.RuleFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.apocalypse.thefall.service.character.rules.util.FormulaUtils.EQUIPPED;
import static com.apocalypse.thefall.service.character.rules.util.FormulaUtils.SPECIAL;


@Slf4j
@Component
@RequiredArgsConstructor
public class RuleEngine {


    private final Map<String, Expression> cache = new ConcurrentHashMap<>();

    /**
     * Preloads a list of formula-based entities into the internal cache.
     *
     * @param items a list of formula entities to preload, where each entity must extend {@link FormulaEntity}.
     */
    public <T extends FormulaEntity> void preload(List<T> items) {
        for (T item : items) {
            String formula = item.getFormula();
            if (formula == null || formula.isBlank()) continue;

            try {
                ExpressionBuilder builder = new ExpressionBuilder(formula);

                // Add used variables for formula
                addUsedVariables(builder, formula);

                for (RuleFunction ruleFunction : RuleFunction.values()) {
                    if (formula.toLowerCase().contains(ruleFunction.getName())) {
                        builder.function(ruleFunction.toExp4jFunction());
                    }
                }

                cache.put(item.getCode().name(), builder.build());

            } catch (Exception e) {
                log.error("Failed to compile formula for {}: {}", item.getCode(), e.getMessage());
            }
        }
    }

    private void addUsedVariables(ExpressionBuilder builder, String formula) {
        Arrays.stream(formula.split("[^A-Z0-9_]+"))
                .filter(v -> !v.isBlank())
                .distinct()
                .filter(v -> SPECIAL.contains(v) || EQUIPPED.contains(v))
                .forEach(builder::variable);
    }

    public Expression get(String code) {
        Expression expr = cache.get(code);
        if (expr == null) throw new IllegalArgumentException("Missing formula for code: " + code);
        return expr;
    }
}
