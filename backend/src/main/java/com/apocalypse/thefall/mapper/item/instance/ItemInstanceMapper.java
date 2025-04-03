package com.apocalypse.thefall.mapper.item.instance;

import com.apocalypse.thefall.dto.item.instance.AmmoInstanceDto;
import com.apocalypse.thefall.dto.item.instance.ArmorInstanceDto;
import com.apocalypse.thefall.dto.item.instance.ItemInstanceDto;
import com.apocalypse.thefall.dto.item.instance.WeaponInstanceDto;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.mapper.item.ItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ItemInstanceMapper {
    @Mapping(target = "item", source = ".")
    ItemInstanceDto toDto(ItemInstance itemInstance);

    @Mapping(target = "item", source = ".")
    ArmorInstanceDto toDto(ArmorInstance armorInstance);

    @Mapping(target = "item", source = ".")
    WeaponInstanceDto toDto(WeaponInstance weaponInstance);

    @Mapping(target = "item", source = ".")
    AmmoInstanceDto toDto(AmmoInstance ammoInstance);
}