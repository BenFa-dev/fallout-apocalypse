package com.apocalypse.thefall.dto.item.instance;

import com.apocalypse.thefall.dto.item.ItemDto;

public record AmmoInstanceDto(
        Long id,
        Integer quantity,
        ItemDto item) implements ItemInstanceDto {
}