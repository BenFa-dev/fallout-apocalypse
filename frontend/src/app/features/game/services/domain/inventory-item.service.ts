import { Injectable } from '@angular/core';
import { EquippedSlot, Inventory, ItemInstance } from '@features/game/models/inventory/inventory.model';

@Injectable({ providedIn: 'root' })
export class InventoryItemService {
	/** Retourne l'item équipé pour le slot donné, ou null s'il est vide. */
	public findEquipped<T extends ItemInstance>(inventory: Inventory, slot: EquippedSlot): T | null {
		return inventory?.items.find(
			(item) => (item as ItemInstance & { equippedSlot: EquippedSlot }).equippedSlot === slot
		) as T | null;
	}
}
