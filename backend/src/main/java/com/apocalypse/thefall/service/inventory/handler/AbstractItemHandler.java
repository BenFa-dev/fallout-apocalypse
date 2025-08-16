package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.config.GameProperties;
import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance;
import com.apocalypse.thefall.entity.character.stats.enums.DerivedStatEnum;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.service.inventory.factory.ItemInstanceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public abstract class AbstractItemHandler<T extends Item, I extends ItemInstance> implements ItemHandler<T, I> {
    protected final ItemInstanceFactory itemInstanceFactory;
    protected final GameProperties gameProperties;

    @Override
    public abstract I createInstance(T item);

    @Override
    public abstract void validateRequirements(Character character, T item);

    @Override
    public abstract void equip(Character character, I itemInstance, EquippedSlot slot);

    @Override
    public abstract void unequip(Character character, I itemInstance);

    protected void validateWeight(Character character, Item item) {
        int carryWeight = character.getDerivedStats().stream()
                .filter(stat -> stat.getCode() == DerivedStatEnum.CARRY_WEIGHT)
                .map(DerivedStatInstance::getCalculatedValue)
                .findFirst()
                .orElseThrow(() -> new GameException("error.game.inventory.noCarryWeight", HttpStatus.INTERNAL_SERVER_ERROR));

        double totalWeight = character.getInventory().getCurrentWeight() + item.getWeight();

        if (totalWeight > carryWeight) {
            throw new GameException("error.game.inventory.tooHeavy", HttpStatus.BAD_REQUEST);
        }
    }

    protected void validateActionPoints(Character character, int requiredActionPoints) {
        if (character.getCurrentStats().getActionPoints() < requiredActionPoints) {
            throw new GameException("error.game.character.notEnoughActionPoints", HttpStatus.BAD_REQUEST);
        }
    }

    protected void consumeActionPoints(Character character, int actionPoints) {
        character.getCurrentStats().setActionPoints(character.getCurrentStats().getActionPoints() - actionPoints);
    }

    protected int getEquipActionPointsCost() {
        return gameProperties.getInventory().getActionPoints().getEquip();
    }

    protected int getUnequipActionPointsCost() {
        return gameProperties.getInventory().getActionPoints().getUnequip();
    }
}