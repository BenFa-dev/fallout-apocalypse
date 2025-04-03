package com.apocalypse.thefall.dto.item.instance;

import com.apocalypse.thefall.dto.item.ItemDto;

public record ItemInstanceDto(
        Long id,
        ItemDto item) {
}