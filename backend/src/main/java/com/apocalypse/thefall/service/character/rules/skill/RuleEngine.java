package com.apocalypse.thefall.service.character.rules.skill;

import com.apocalypse.thefall.entity.character.stats.enums.SkillEnum;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.repository.character.stats.SkillRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RuleEngine {

    private final SkillRepository skillRepository;

    // Cache des expressions compilées
    private final Map<SkillEnum, Expression> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void preloadAllRules() {
        log.info("Preloading visible skill formulas into memory...");

        skillRepository.findAllByVisibleTrueOrderByDisplayOrderAsc().stream()
                .filter(skill -> skill.getFormula() != null && !skill.getFormula().isBlank())
                .forEach(skill -> {
                    try {
                        Expression expression = compile(skill.getFormula());
                        cache.put(skill.getCode(), expression);
                    } catch (Exception e) {
                        log.error("Error compiling formula for skill {}: {}", skill.getCode(), skill.getFormula(), e);
                    }
                });

        log.info("Loaded {} skill formulas.", cache.size());
    }

    public Expression getCompiledExpression(SkillEnum skillCode) {
        Expression expression = cache.get(skillCode);
        if (expression == null) {
            throw new IllegalArgumentException("Skill formula for " + skillCode + " not found or not visible.");
        }
        return expression;
    }

    private Expression compile(String formula) {
        return new ExpressionBuilder(formula)
                .variables(Arrays.stream(SpecialEnum.values()).map(Enum::name).toArray(String[]::new))
                .build();
    }
}
