package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.stats.DerivedStat
import com.apocalypse.thefall.repository.character.stats.DerivedStatRepository
import com.apocalypse.thefall.service.character.rules.RuleEngine
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class DerivedStatService(
    private val derivedStatRepository: DerivedStatRepository,
    private val ruleEngine: RuleEngine
) {

    private lateinit var derivedStats: List<DerivedStat>

    @PostConstruct
    fun init() {
        derivedStats = derivedStatRepository.findAll()
        ruleEngine.preload(derivedStats)
    }

    @Transactional(readOnly = true)
    open fun findAll(): List<DerivedStat> =
        derivedStatRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()
}
