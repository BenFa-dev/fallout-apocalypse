package com.apocalypse.thefall.service.inventory.handler;

import com.apocalypse.thefall.entity.Character;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.item.Item;

public interface ItemHandler<T extends Item, I extends ItemInstance> {
    I createInstance(T item);

    void validateRequirements(Character character, T item);

    void equip(Character character, I itemInstance);

    void unequip(Character character, I itemInstance);
}