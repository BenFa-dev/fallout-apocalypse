package com.apocalypse.thefall.service;

import com.apocalypse.thefall.event.CharacterMovementEvent;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.model.Character;
import com.apocalypse.thefall.model.Map;
import com.apocalypse.thefall.model.Tile;
import com.apocalypse.thefall.repository.CharacterRepository;
import com.apocalypse.thefall.repository.TileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final MapService mapService;
    private final EventService eventService;
    private final TileRepository tileRepository;
    private final Random random = new Random();

    @Transactional
    public Character createCharacter(String userId, Character character) {
        Map map = mapService.getOrCreateMap();

        character.setUserId(userId);
        character.setCurrentMap(map);

        // Position de départ aléatoire
        do {
            character.setCurrentX(random.nextInt(map.getWidth()));
            character.setCurrentY(random.nextInt(map.getHeight()));
        } while (isValidPosition(character.getCurrentX(), character.getCurrentY(), map));

        // Points d'action initiaux
        character.setMaxActionPoints(character.getSpecial().calculateActionPoints());
        character.setCurrentActionPoints(character.getMaxActionPoints());

        return characterRepository.save(character);
    }

    @Transactional(readOnly = true)
    public Character getCharacterByUserId(String userId) {
        return characterRepository.findByUserId(userId).orElse(null);
    }

    @Transactional
    public Character moveCharacter(String userId, int newX, int newY) {
        Character character = getCharacterByUserId(userId);

        if (character == null) {
            throw new GameException("error.game.character.notFound", HttpStatus.NOT_FOUND, userId);
        }

        if (!isValidMove(character, newX, newY)) {
            throw new GameException("error.game.movement.invalidPosition", HttpStatus.BAD_REQUEST);
        }

        int movementCost = calculateMovementCost(character.getCurrentMap(), newX, newY);
        if (character.getCurrentActionPoints() < movementCost) {
            throw new GameException("error.game.movement.insufficientAp", HttpStatus.BAD_REQUEST,
                    String.valueOf(movementCost - character.getCurrentActionPoints()));
        }

        character.setCurrentX(newX);
        character.setCurrentY(newY);
        character.setCurrentActionPoints(character.getCurrentActionPoints() - movementCost);

        character = characterRepository.save(character);

        // Publier l'événement via le service dédié
        eventService.publishMovementEvent(
                CharacterMovementEvent.of(
                        userId,
                        character.getId(),
                        newX,
                        newY,
                        character.getCurrentActionPoints()
                )
        );

        return character;
    }

    private boolean isValidMove(Character character, int newX, int newY) {
        if (isValidPosition(newX, newY, character.getCurrentMap())) {
            return false;
        }
        return Math.abs(newX - character.getCurrentX()) + Math.abs(newY - character.getCurrentY()) == 1;
    }

    private boolean isValidPosition(int x, int y, Map map) {
        return x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight();
    }

    private int calculateMovementCost(Map map, int x, int y) {
        Tile tile = tileRepository.findByMapAndXAndY(map, x, y)
                .orElseThrow(() -> new GameException("error.resourceNotFound", HttpStatus.NOT_FOUND, "Tile",
                        "position", x + "," + y));

        if (!tile.isWalkable()) {
            throw new GameException("error.game.movement.invalidTile", HttpStatus.BAD_REQUEST);
        }

        return tile.getMovementCost();
    }
}