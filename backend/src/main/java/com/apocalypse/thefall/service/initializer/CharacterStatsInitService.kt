package com.apocalypse.thefall.service.initializer

import com.apocalypse.thefall.repository.character.stats.DerivedStatRepository
import com.apocalypse.thefall.repository.character.stats.SkillRepository
import com.apocalypse.thefall.service.character.rules.RuleEngine
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CharacterStatsInitService(
    private val derivedStatRepo: DerivedStatRepository,
    private val skillRepo: SkillRepository,
    private val ruleEngine: RuleEngine
) {
    private val log = LoggerFactory.getLogger(CharacterStatsInitService::class.java)

    /**
     * Preload character stats into 2nd level cache and register
     * immutable definitions in the rule engine.
     */
    @Transactional(readOnly = true)
    open fun preloadAll() {
        log.info("⚙️  Preloading character stats...")

        val derivedStats = derivedStatRepo.findAll()
        ruleEngine.preload(derivedStats)
        log.info("📊  Loaded {} derived stats", derivedStats.size)

        val skills = skillRepo.findAll()
        ruleEngine.preload(skills)
        log.info("📚  Loaded {} skills", skills.size)

        log.info("✅  Character stats successfully preloaded")
    }
}
