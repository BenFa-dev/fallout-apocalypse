package com.apocalypse.thefall.entity.character.stats

import com.apocalypse.thefall.entity.character.stats.enums.DerivedStatEnum
import com.apocalypse.thefall.service.character.rules.CalculatedInstance

open class DerivedStatInstance(
    var derivedStat: DerivedStat,
    override var value: Int = 0
) : CalculatedInstance<DerivedStatEnum> {

    @Transient
    override var calculatedValue: Int? = null

    override val code: DerivedStatEnum get() = derivedStat.code
}
