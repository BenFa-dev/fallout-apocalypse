package com.apocalypse.thefall.service.character.rules;

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RuleEngine {
    private static final Function MAX_FUNCTION = new Function("max", 2) {
        @Override
        public double apply(double... args) {
            return Math.max(args[0], args[1]);
        }
    };

    private static final String[] SPECIAL =
            java.util.Arrays.stream(SpecialEnum.values())
                    .map(Enum::name)
                    .toArray(String[]::new);

    private final Map<String, Expression> cache = new ConcurrentHashMap<>();

    public <T extends FormulaEntity> void preload(List<T> items) {
        for (T item : items) {
            String formula = item.getFormula();
            if (formula == null || formula.isBlank()) continue;

            try {
                Expression expr = new ExpressionBuilder(formula)
                        .variables(SPECIAL)
                        .function(MAX_FUNCTION)
                        .build();
                cache.put(item.getCode().name(), expr);
            } catch (Exception e) {
                log.error("Failed to compile formula for {}: {}", item.getCode(), e.getMessage());
            }
        }
    }

    public Expression get(String code) {
        Expression expr = cache.get(code);
        if (expr == null) throw new IllegalArgumentException("Missing formula for code: " + code);
        return expr;
    }
}
