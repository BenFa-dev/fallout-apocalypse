package com.apocalypse.thefall.service.character.rules.util

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.entity.instance.ItemInstance
import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.entity.item.enums.EquippedSlot
import net.objecthunter.exp4j.Expression
import org.slf4j.LoggerFactory

object FormulaUtils {

    private val log = LoggerFactory.getLogger(FormulaUtils::class.java)

    const val EQUIPPED_PREFIX: String = "EQUIPPED_"

    val SPECIAL: Set<String> = SpecialEnum.entries.map { it.name }.toSet()

    // Used to define specific properties for equipped item, for ARMOR or WEAPON.
    val EQUIPPED_PROPERTIES: Map<EquippedSlot, List<String>> = mapOf(EquippedSlot.ARMOR to listOf("ARMOR_CLASS"))

    val EQUIPPED: Set<String> =
        EQUIPPED_PROPERTIES.flatMap { (slot, props) -> props.map { "$EQUIPPED_PREFIX${slot.name}_$it" } }.toSet()

    /**
     * Inject variables from expression
     */
    fun injectVariables(
        expr: Expression,
        variableNames: Set<String>,
        specialValues: Map<SpecialEnum, Int>,
        equippedItems: Map<EquippedSlot, ItemInstance>
    ) {
        for (v in variableNames) {
            when {
                SPECIAL.contains(v) -> injectSpecialVariable(expr, v, specialValues)
                EQUIPPED.contains(v) -> injectEquippedVariable(expr, v, equippedItems)
                else -> log.error("Unknown variable in the formula: {}", v)
            }
        }
    }

    /**
     * Injects the special values into the expression if available.
     */
    private fun injectSpecialVariable(
        expr: Expression,
        varString: String,
        specialValues: Map<SpecialEnum, Int>
    ) {
        try {
            val special = SpecialEnum.valueOf(varString)
            val value = specialValues[special]
            if (value != null) {
                expr.setVariable(varString, value.toDouble())
            } else {
                log.warn("Missing value for SPECIAL variable: {}", varString)
            }
        } catch (_: IllegalArgumentException) {
            log.error("Invalid SPECIAL enum: {}", varString)
        }
    }

    /**
     * Injects the equipped values into the expression if available.
     */
    fun injectEquippedVariable(
        expr: Expression,
        varString: String,
        equippedItems: Map<EquippedSlot, ItemInstance>
    ) {
        if (!varString.startsWith(EQUIPPED_PREFIX)) {
            log.warn("EQUIPPED variable does not start with '{}': {}", EQUIPPED_PREFIX, varString)
            return
        }

        val cleaned = varString.removePrefix(EQUIPPED_PREFIX)
        val idx = cleaned.indexOf('_')
        if (idx < 0) {
            log.warn("Malformed EQUIPPED variable (missing slot/property): {}", varString)
            return
        }

        val slotName = cleaned.take(idx)          // ex: ARMOR
        val propertyName = cleaned.substring(idx + 1)     // ex: ARMOR_CLASS

        try {
            val slot = EquippedSlot.valueOf(slotName)
            val instance = equippedItems[slot]
            if (instance == null) {
                log.info("No item equipped in slot: {}", slot)
                expr.setVariable(varString, 0.0)
                return
            }

            val value = extractItemProperty(instance, propertyName)
            expr.setVariable(varString, value.toDouble())
        } catch (_: IllegalArgumentException) {
            log.error("Invalid equipped slot name: {}", slotName)
        }
    }

    /**
     * Get corresponding value form the property required by the formula.
     * For example, if itemInstance is an Armor and propertyName is "ARMOR_CLASS", then this method will return the armor class.
     */
    private fun extractItemProperty(itemInstance: ItemInstance, propertyName: String): Int {
        return try {
            val item: Item = itemInstance.item ?: return 0

            // "ARMOR_CLASS" -> "getArmorClass"
            val methodName = buildString {
                append("get")
                propertyName.lowercase().split('_').forEach { part ->
                    if (part.isNotEmpty()) {
                        append(part.replaceFirstChar { it.uppercase() })
                    }
                }
            }

            val value = item::class.java.getMethod(methodName).invoke(item)
            (value as? Number)?.toInt() ?: 0
        } catch (e: Exception) {
            log.warn("Failed to read item property '{}': {}", propertyName, e.message)
            0
        }
    }
}
