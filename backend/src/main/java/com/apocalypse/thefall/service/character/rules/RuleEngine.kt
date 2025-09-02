package com.apocalypse.thefall.service.character.rules

import com.apocalypse.thefall.service.character.rules.util.FormulaUtils.EQUIPPED
import com.apocalypse.thefall.service.character.rules.util.FormulaUtils.SPECIAL
import com.apocalypse.thefall.service.character.rules.util.RuleFunction
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
open class RuleEngine {

    private val log = LoggerFactory.getLogger(RuleEngine::class.java)

    private val cache: MutableMap<String, Expression> = ConcurrentHashMap()

    /**
     * Preloads a list of formula-based entities into the internal cache.
     *
     * @param items a list of formula entities to preload, where each entity must extend FormulaEntity.
     */
    open fun <T : FormulaEntity> preload(items: List<T>) {
        for (item in items) {
            val formula = item.formula
            if (formula.isBlank()) continue

            try {
                val builder = ExpressionBuilder(formula)

                // Add used variables for formula
                addUsedVariables(builder, formula)

                // Register custom functions used in the formula
                for (ruleFunction in RuleFunction.entries) {
                    if (formula.contains(ruleFunction.name, ignoreCase = true)) {
                        builder.function(ruleFunction.toExp4jFunction())
                    }
                }

                cache[item.code.name] = builder.build()
            } catch (e: Exception) {
                log.error("Failed to compile formula for {}: {}", item.code, e.message)
            }
        }
    }

    private fun addUsedVariables(builder: ExpressionBuilder, formula: String) {
        formula.split(Regex("[^A-Z0-9_]+"))
            .asSequence()
            .filter { it.isNotBlank() }
            .distinct()
            .filter { it in SPECIAL || it in EQUIPPED }
            .forEach { builder.variable(it) }
    }

    open fun get(code: String): Expression =
        cache[code] ?: throw IllegalArgumentException("Missing formula for code: $code")
}
