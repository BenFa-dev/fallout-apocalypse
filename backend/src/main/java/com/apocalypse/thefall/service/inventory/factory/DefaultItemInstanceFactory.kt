package com.apocalypse.thefall.service.inventory.factory;

import com.apocalypse.thefall.entity.instance.AmmoInstance;
import com.apocalypse.thefall.entity.instance.ArmorInstance;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.instance.WeaponInstance;
import com.apocalypse.thefall.entity.item.Ammo;
import com.apocalypse.thefall.entity.item.Armor;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.Weapon;
import org.springframework.stereotype.Component;

@Component
public class DefaultItemInstanceFactory implements ItemInstanceFactory {

    @Override
    public ItemInstance createInstance(Item item) {
        if (item instanceof Weapon) {
            return WeaponInstance.builder()
                    .item(item)
                    .currentAmmoQuantity(0)
                    .build();
        } else if (item instanceof Armor) {
            return ArmorInstance.builder()
                    .item(item)
                    .build();
        } else if (item instanceof Ammo) {
            return AmmoInstance.builder()
                    .item(item)
                    .quantity(0)
                    .build();
        }
        throw new IllegalArgumentException("Unsupported item type: " + item.getClass().getSimpleName());
    }
}