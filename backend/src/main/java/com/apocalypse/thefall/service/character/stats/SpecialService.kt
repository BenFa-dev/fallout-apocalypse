package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Special
import com.apocalypse.thefall.entity.character.stats.SpecialInstance
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum
import com.apocalypse.thefall.repository.character.stats.SpecialRepository
import com.apocalypse.thefall.util.MapUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class SpecialService(
    private val specialRepository: SpecialRepository
) {

    @Transactional(readOnly = true)
    open fun findAll(): List<Special> = specialRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()

    fun getSpecialValuesMap(character: Character): Map<SpecialEnum, Int> {
        return MapUtils.extractMap(
            character.specials,
            { instance -> instance.special.code },
            SpecialInstance::value
        )
    }
}
