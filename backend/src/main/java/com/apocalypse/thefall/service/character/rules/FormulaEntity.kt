package com.apocalypse.thefall.service.character.rules

interface FormulaEntity {
    val code: Enum<*>
    val formula: String
}
