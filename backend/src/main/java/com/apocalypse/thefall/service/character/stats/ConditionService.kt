package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.stats.Condition
import com.apocalypse.thefall.repository.character.stats.ConditionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ConditionService(
    private val conditionRepository: ConditionRepository
) {
    @Transactional(readOnly = true)
    open fun findAll(): List<Condition> {
        return conditionRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()
    }
}
