import { Injectable } from '@angular/core';
import {
	Ammo,
	AmmoInstance,
	Armor,
	ArmorInstance,
	Item,
	ItemInstance,
	ItemType,
	Weapon,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';

@Injectable({ providedIn: 'root' })
export class CastItemService {
	// Item (base)
	public asItem<T extends Item>(item: Item | null | undefined, type: ItemType): T | null {
		return item?.type === type ? (item as T) : null;
	}

	public asAmmo(item: Item | null | undefined): Ammo | null {
		return this.asItem<Ammo>(item, ItemType.AMMO);
	}

	public asWeapon(item: Item | null | undefined): Weapon | null {
		return this.asItem<Weapon>(item, ItemType.WEAPON);
	}

	public asArmor(item: Item | null | undefined): Armor | null {
		return this.asItem<Armor>(item, ItemType.ARMOR);
	}

	// ItemInstance (avec item dedans)
	public asItemInstance<T extends ItemInstance>(instance: ItemInstance | null | undefined, type: ItemType): T | null {
		return instance?.item?.type === type ? (instance as T) : null;
	}

	public asAmmoInstance(instance: ItemInstance | null | undefined): AmmoInstance | null {
		return this.asItemInstance<AmmoInstance>(instance, ItemType.AMMO);
	}

	public asWeaponInstance(instance: ItemInstance | null | undefined): WeaponInstance | null {
		return this.asItemInstance<WeaponInstance>(instance, ItemType.WEAPON);
	}

	public asArmorInstance(instance: ItemInstance | null | undefined): ArmorInstance | null {
		return this.asItemInstance<ArmorInstance>(instance, ItemType.ARMOR);
	}
}
