package com.apocalypse.thefall.mapper.item;

import com.apocalypse.thefall.dto.item.*;
import com.apocalypse.thefall.entity.item.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toDto(Item item);

    @Mapping(target = "item", source = ".")
    WeaponDto toWeaponDto(Weapon weapon);

    WeaponModeDto toWeaponModeDto(WeaponMode weaponMode);

    @Mapping(target = "item", source = ".")
    ArmorDto toArmorDto(Armor armor);

    @Mapping(target = "item", source = ".")
    AmmoDto toAmmoDto(Ammo ammo);
}