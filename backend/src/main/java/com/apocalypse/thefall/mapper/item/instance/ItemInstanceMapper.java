package com.apocalypse.thefall.mapper.item.instance;

import com.apocalypse.thefall.dto.item.WeaponModeDto;
import com.apocalypse.thefall.dto.item.instance.AmmoInstanceDto;
import com.apocalypse.thefall.dto.item.instance.ArmorInstanceDto;
import com.apocalypse.thefall.dto.item.instance.ItemInstanceDto;
import com.apocalypse.thefall.dto.item.instance.WeaponInstanceDto;
import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.Weapon;
import com.apocalypse.thefall.entity.item.WeaponMode;
import com.apocalypse.thefall.mapper.item.ItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ItemInstanceMapper {
    // Polymorphic dispatcher
    default ItemInstanceDto toDto(ItemInstance instance) {
        if (instance instanceof WeaponInstance w) return toDto(w);
        if (instance instanceof ArmorInstance a) return toDto(a);
        if (instance instanceof AmmoInstance am) return toDto(am);
        return null;
    }

    ArmorInstanceDto toDto(ArmorInstance armorInstance);

    @Mapping(target = "currentWeaponMode", expression = "java(resolveWeaponMode(weaponInstance))")
    WeaponInstanceDto toDto(WeaponInstance weaponInstance);

    AmmoInstanceDto toDto(AmmoInstance ammoInstance);

    default WeaponModeDto resolveWeaponMode(WeaponInstance weaponInstance) {
        WeaponMode current = weaponInstance.getCurrentWeaponMode();
        if (current != null) return map(current);

        Item item = weaponInstance.getItem();

        if (item instanceof Weapon weapon) {
            return weapon.getModes()
                    .stream()
                    .findFirst()
                    .map(this::map)
                    .orElse(null);
        }

        return null;
    }


    WeaponModeDto map(WeaponMode mode);

}