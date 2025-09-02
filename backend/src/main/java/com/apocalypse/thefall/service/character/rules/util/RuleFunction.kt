package com.apocalypse.thefall.service.character.rules.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.function.Function;

@Getter
@RequiredArgsConstructor
public enum RuleFunction {
    MAX("max", 2, args -> Math.max(args[0], args[1])),
    MIN("min", 2, args -> Math.min(args[0], args[1]));

    private final String name;
    private final int argumentCount;
    private final DoubleFunction function;

    public Function toExp4jFunction() {
        return new Function(name, argumentCount) {
            @Override
            public double apply(double... args) {
                return function.apply(args);
            }
        };
    }

    @FunctionalInterface
    public interface DoubleFunction {
        double apply(double... args);
    }
}