package com.apocalypse.thefall.service.character.rules

interface CalculatedInstance<T : Enum<T>> {
    val code: T
    val value: Int
    var calculatedValue: Int?
}
