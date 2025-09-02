package com.apocalypse.thefall.service.character.rules.util

import net.objecthunter.exp4j.function.Function

enum class RuleFunction(
    val functionName: String,
    val argumentCount: Int,
    private val function: (DoubleArray) -> Double
) {
    MAX("max", 2, { args -> maxOf(args[0], args[1]) }),
    MIN("min", 2, { args -> minOf(args[0], args[1]) });

    fun toExp4jFunction(): Function {
        return object : Function(functionName, argumentCount) {
            override fun apply(vararg args: Double): Double {
                return function(args)
            }
        }
    }
}
