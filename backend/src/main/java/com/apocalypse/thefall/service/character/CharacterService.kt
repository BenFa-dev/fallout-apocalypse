package com.apocalypse.thefall.service.character;

import com.apocalypse.thefall.entity.GameMap;
import com.apocalypse.thefall.entity.Tile;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.event.CharacterMovementEvent;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.TileRepository;
import com.apocalypse.thefall.repository.character.CharacterRepository;
import com.apocalypse.thefall.service.EventService;
import com.apocalypse.thefall.service.GenerateMapService;
import com.apocalypse.thefall.service.character.rules.FormulaEngine;
import com.apocalypse.thefall.service.character.stats.DerivedStatService;
import com.apocalypse.thefall.service.character.stats.SpecialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final DerivedStatService derivedStatService;
    private final EventService eventService;
    private final FormulaEngine formulaEngine;
    private final GenerateMapService generateMapService;
    private final SpecialService specialService;
    private final TileRepository tileRepository;

    private final Random random = new Random();

    @Transactional
    public Character createCharacter(String userId, Character character) {
        GameMap map = generateMapService.getOrCreateMap();

        character.setUserId(userId);
        character.setCurrentMap(map);

        // Random start position
        do {
            character.setCurrentX(random.nextInt(map.getWidth()));
            character.setCurrentY(random.nextInt(map.getHeight()));
        } while (isValidPosition(character.getCurrentX(), character.getCurrentY(), map));

        return characterRepository.save(character);
    }

    @Transactional(readOnly = true)
    public Character getSimpleCharacterByUserId(String userId) {
        return characterRepository.findSimpleByUserId(userId).orElseThrow(() -> new GameException("error.game.user.notFound", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Character getCharacterByUserId(String userId) {
        Character character = characterRepository.findByUserId(userId).orElseThrow(() -> new GameException("error.game.user.notFound", HttpStatus.NOT_FOUND));
        return getCalculatedStatsForCharacter(character);
    }

    /**
     * Calcul player stats from formulas
     * - Derived stats
     * - Skills stats
     */
    public Character getCalculatedStatsForCharacter(Character character) {
        Map<SpecialEnum, Integer> specialValues = specialService.getSpecialValuesMap(character);
        Map<EquippedSlot, ItemInstance> equippedItems = character.getInventory().getEquippedItemsBySlot();

        formulaEngine.compute(character.getSkills(), specialValues, equippedItems);
        character.setDerivedStats(derivedStatService.getDefaultZeroDerivedStatsInstance());
        formulaEngine.compute(character.getDerivedStats(), specialValues, equippedItems);
        return character;
    }

    @Transactional(readOnly = true)
    public Character getCharacterById(Long id) {
        return characterRepository.findById(id).orElseThrow(() -> new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Character moveCharacter(String userId, int newX, int newY) {
        Character character = getSimpleCharacterByUserId(userId);

        if (character == null) {
            throw new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND, userId);
        }

        if (!isValidMove(character, newX, newY)) {
            throw new GameException("error.game.movement.invalidPosition", HttpStatus.BAD_REQUEST);
        }

        int movementCost = calculateMovementCost(character.getCurrentMap(), newX, newY);
        if (character.getCurrentStats().getActionPoints() < movementCost) {
            throw new GameException("error.game.movement.insufficientAp", HttpStatus.BAD_REQUEST, String.valueOf(movementCost - character.getCurrentStats().getActionPoints()));
        }

        character.setCurrentX(newX);
        character.setCurrentY(newY);
        character.getCurrentStats().setActionPoints(character.getCurrentStats().getActionPoints() - movementCost);

        character = characterRepository.save(character);

        eventService.publishMovementEvent(CharacterMovementEvent.of(userId, character.getId(), newX, newY, character.getCurrentStats().getActionPoints()));

        return character;
    }

    private boolean isValidMove(Character character, int newX, int newY) {
        if (isValidPosition(newX, newY, character.getCurrentMap())) {
            return false;
        }
        return Math.abs(newX - character.getCurrentX()) + Math.abs(newY - character.getCurrentY()) == 1;
    }

    private boolean isValidPosition(int x, int y, GameMap map) {
        return x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight();
    }

    private int calculateMovementCost(GameMap map, int x, int y) {
        Tile tile = tileRepository.findByMapAndXAndY(map, x, y).orElseThrow(() -> new GameException("error.resourceNotFound", HttpStatus.NOT_FOUND, "Tile", "position", x + "," + y));

        if (!tile.isWalkable()) {
            throw new GameException("error.game.movement.invalidTile", HttpStatus.BAD_REQUEST);
        }

        return tile.getMovementCost();
    }

    public Character save(Character character) {
        return getCalculatedStatsForCharacter(characterRepository.save(character));
    }
}