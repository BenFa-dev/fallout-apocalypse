package com.apocalypse.thefall.dto.item;


import com.apocalypse.thefall.entity.item.enums.ItemType;

import java.util.Map;

public interface ItemDto {
    Long id();

    ItemType type();

    Map<String, String> names();

    Map<String, String> descriptions();

    double weight();

    int basePrice();

    String path();
}