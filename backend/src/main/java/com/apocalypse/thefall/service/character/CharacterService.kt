package com.apocalypse.thefall.service.character

import com.apocalypse.thefall.entity.GameMap
import com.apocalypse.thefall.entity.character.Character
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance
import com.apocalypse.thefall.event.CharacterMovementEvent
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.TileRepository
import com.apocalypse.thefall.repository.character.CharacterRepository
import com.apocalypse.thefall.service.EventService
import com.apocalypse.thefall.service.GenerateMapService
import com.apocalypse.thefall.service.character.rules.FormulaEngine
import com.apocalypse.thefall.service.character.stats.DerivedStatService
import com.apocalypse.thefall.service.character.stats.SpecialService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.math.abs

@Service
open class CharacterService(
    private val characterRepository: CharacterRepository,
    private val derivedStatService: DerivedStatService,
    private val eventService: EventService,
    private val formulaEngine: FormulaEngine,
    private val generateMapService: GenerateMapService,
    private val specialService: SpecialService,
    private val tileRepository: TileRepository,
    private val random: Random = Random()
) {

    @Transactional
    open fun createCharacter(userId: String, character: Character): Character {
        val map = generateMapService.getOrCreateMap()

        character.userId = userId
        character.currentMap = map

        // Random start position (retry while position is invalid/out of bounds)
        do {
            character.currentX = random.nextInt(map.width)
            character.currentY = random.nextInt(map.height)
        } while (isOutOfBounds(character.currentX, character.currentY, map))

        return characterRepository.save(character)
    }

    @Transactional(readOnly = true)
    open fun getSimpleCharacterByUserId(userId: String): Character =
        characterRepository.findSimpleByUserId(userId)
            ?: throw GameException("error.game.user.notFound", HttpStatus.NOT_FOUND)

    @Transactional(readOnly = true)
    open fun getCharacterByUserId(userId: String): Character {
        val character = characterRepository.findByUserIdForInventory(userId)
            ?: throw GameException("error.game.user.notFound", HttpStatus.NOT_FOUND)
        return getCalculatedStatsForCharacter(character)
    }

    /**
     * Calcul player stats from formulas
     * - Derived stats
     * - Skills stats
     */
    open fun getCalculatedStatsForCharacter(character: Character): Character {
        val specialValues = specialService.getSpecialValuesMap(character)
        val equippedItems = character.inventory?.getEquippedItemsBySlot() ?: emptyMap()

        // 1) skills
        formulaEngine.compute(character.skills, specialValues, equippedItems)

        // 2) derived stats
        val zeroInstances = derivedStatService.findAll().map { stat ->
            DerivedStatInstance(derivedStat = stat, value = 0)
        }.toMutableSet()

        character.derivedStats = zeroInstances

        formulaEngine.compute(character.derivedStats, specialValues, equippedItems)
        return character
    }


    @Transactional(readOnly = true)
    open fun getCharacterById(id: Long): Character =
        characterRepository.findById(id)
            .orElseThrow { GameException("error.game.character.notFound", HttpStatus.NOT_FOUND) }

    @Transactional
    open fun moveCharacter(userId: String, newX: Int, newY: Int): Character {
        var character = getSimpleCharacterByUserId(userId)

        if (!isValidMove(character, newX, newY)) {
            throw GameException("error.game.movement.invalidPosition", HttpStatus.BAD_REQUEST)
        }

        val movementCost = calculateMovementCost(requireNotNull(character.currentMap), newX, newY)

        val currentStats = requireNotNull(character.currentStats) {
            "Character currentStats is null"
        }
        if (currentStats.actionPoints < (movementCost ?: 0)) {
            throw GameException(
                "error.game.movement.insufficientAp",
                HttpStatus.BAD_REQUEST,
                (movementCost?.minus(currentStats.actionPoints)).toString()
            )
        }

        character.currentX = newX
        character.currentY = newY
        currentStats.actionPoints -= (movementCost ?: 0)

        character = characterRepository.save(character)

        eventService.publishMovementEvent(
            CharacterMovementEvent.of(
                userId,
                character.id,
                newX,
                newY,
                currentStats.actionPoints
            )
        )

        return character
    }

    private fun isValidMove(character: Character, newX: Int, newY: Int): Boolean {
        val map = requireNotNull(character.currentMap) { "Character currentMap is null" }
        if (isOutOfBounds(newX, newY, map)) return false
        return abs(newX - character.currentX) + abs(newY - character.currentY) == 1
    }

    private fun isOutOfBounds(x: Int, y: Int, map: GameMap): Boolean =
        x < 0 || x >= map.width || y < 0 || y >= map.height

    private fun calculateMovementCost(map: GameMap, x: Int, y: Int): Int? {
        val tile = tileRepository.findByMapAndXAndY(map, x, y)
            ?: throw GameException(
                "error.resourceNotFound",
                HttpStatus.NOT_FOUND,
                "Tile",
                "position",
                "$x,$y"
            )

        if (tile.isWalkable == false) {
            throw GameException("error.game.movement.invalidTile", HttpStatus.BAD_REQUEST)
        }

        return tile.movementCost
    }

    @Transactional
    open fun save(character: Character): Character = getCalculatedStatsForCharacter(characterRepository.save(character))
}
