package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.stats.Skill
import com.apocalypse.thefall.repository.character.stats.SkillRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class SkillService(
    private val skillRepository: SkillRepository,
) {

    @Transactional(readOnly = true)
    open fun findAll(): List<Skill> {
        return skillRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()
    }
}
