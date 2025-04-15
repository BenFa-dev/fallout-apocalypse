import { inject, Injectable } from '@angular/core';
import {
	ArmorInstance,
	EquippedSlot,
	Inventory,
	ItemInstance,
	ItemType,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Injectable({
	providedIn: 'root'
})
export class InventoryItemService {
	private readonly asItemPipe = inject(AsItemPipe)

	/** Retourne l'item équipé pour le slot donné, ou null s'il est vide. */
	public findEquipped<T extends ItemInstance>(inventory: Inventory, slot: EquippedSlot): T | null {
		return inventory?.items.find(
			(item) => (item as ItemInstance & { equippedSlot: EquippedSlot }).equippedSlot === slot
		) as T | null;
	}

	public getEquippedItems(inventory: Inventory) {
		const armorInstance = this.findEquipped<ArmorInstance>(inventory, EquippedSlot.ARMOR);
		const primaryWeaponInstance = this.findEquipped<WeaponInstance>(inventory, EquippedSlot.PRIMARY_WEAPON);
		const secondaryWeaponInstance = this.findEquipped<WeaponInstance>(inventory, EquippedSlot.SECONDARY_WEAPON);

		const primaryWeapon = this.asItemPipe.transform(primaryWeaponInstance?.item, ItemType.WEAPON);
		const secondaryWeapon = this.asItemPipe.transform(secondaryWeaponInstance?.item, ItemType.WEAPON);
		const armor = this.asItemPipe.transform(armorInstance?.item, ItemType.ARMOR);

		return {
			armorInstance,
			primaryWeaponInstance,
			secondaryWeaponInstance,
			primaryWeapon,
			secondaryWeapon,
			armor,
			primaryWeaponMode: primaryWeapon?.weaponModes[0],
			secondaryWeaponMode: secondaryWeapon?.weaponModes[0]
		};
	}

	public equipItem(itemInstance: ItemInstance, target: EquippedSlot) {
		return null;
	}

}
