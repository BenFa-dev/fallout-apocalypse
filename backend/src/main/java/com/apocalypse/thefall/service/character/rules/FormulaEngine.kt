package com.apocalypse.thefall.service.character.rules

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import com.apocalypse.thefall.service.character.rules.util.FormulaUtils
import net.objecthunter.exp4j.Expression
import org.springframework.stereotype.Component

@Component
open class FormulaEngine(
    private val ruleEngine: RuleEngine
) {

    open fun <T : Enum<T>, I : CalculatedInstance<T>> compute(
        instances: Collection<I>,
        specialValues: Map<SpecialEnum, Int>,
        equippedItems: Map<EquippedSlot, ItemInstance>
    ) {
        for (instance in instances) {
            val code: T = instance.code
            val expr: Expression = ruleEngine.get(code.name)

            val variableNames: Set<String> = expr.variableNames

            // Inject variables into the expression
            FormulaUtils.injectVariables(expr, variableNames, specialValues, equippedItems)

            val base = instance.value
            val total = base + expr.evaluate().toInt()

            instance.calculatedValue = total
        }
    }
}
