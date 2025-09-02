package com.apocalypse.thefall.service.character.stats

import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.Perk
import com.apocalypse.thefall.repository.character.stats.PerkRepository
import com.apocalypse.thefall.service.character.rules.perk.PerkEngine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class PerkService(
    private val perkRepository: PerkRepository,
    private val perkEngine: PerkEngine
) {

    @Transactional(readOnly = true)
    open fun findAll(): List<Perk> = perkRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()

    @Transactional(readOnly = true)
    open fun findAvailablePerks(character: Character): List<Perk> {
        val perks = perkRepository.findAllByVisibleTrueOrderByDisplayOrderAsc()
        perkEngine.compute(perks, character)
        return perks
    }
}
