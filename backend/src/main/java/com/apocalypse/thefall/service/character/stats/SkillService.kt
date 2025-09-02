package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.stats.Skill
import com.apocalypse.thefall.repository.character.stats.SkillRepository
import com.apocalypse.thefall.service.character.rules.RuleEngine
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class SkillService(
    private val skillRepository: SkillRepository,
    private val ruleEngine: RuleEngine
) {

    private val log = LoggerFactory.getLogger(SkillService::class.java)

    @PostConstruct
    fun init() {
        val skills: List<Skill> = skillRepository.findAll()

        log.info("Preloading {} skills", skills.size)

        ruleEngine.preload(skills)
    }

    @Transactional(readOnly = true)
    open fun findAll(): List<Skill> {
        return skillRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()
    }
}
