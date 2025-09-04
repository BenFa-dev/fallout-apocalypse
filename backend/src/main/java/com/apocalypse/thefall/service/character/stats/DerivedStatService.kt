package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.stats.DerivedStat
import com.apocalypse.thefall.repository.character.stats.DerivedStatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class DerivedStatService(
    private val derivedStatRepository: DerivedStatRepository,
) {

    @Transactional(readOnly = true)
    open fun findAll(): List<DerivedStat> =
        derivedStatRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()
}
