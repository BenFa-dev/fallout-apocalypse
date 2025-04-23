package com.apocalypse.thefall.mapper.item;

import com.apocalypse.thefall.dto.item.*;
import com.apocalypse.thefall.entity.item.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    // Polymorphic dispatcher
    default ItemDto toDto(Item instance) {
        if (instance instanceof Weapon w) return toDto(w);
        if (instance instanceof Armor a) return toDto(a);
        if (instance instanceof Ammo am) return toDto(am);
        return null;
    }

    @Mapping(target = "weaponModes", source = "modes")
    WeaponDto toDto(Weapon weapon);

    WeaponModeDto toDto(WeaponMode weaponMode);

    ArmorDto toDto(Armor armor);

    AmmoDto toDto(Ammo ammo);
}