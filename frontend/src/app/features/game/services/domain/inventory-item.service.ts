import { inject, Injectable } from '@angular/core';
import {
	EquippedSlot,
	Inventory,
	ItemInstance,
	ItemInstanceKeys
} from '@features/game/models/inventory/inventory.model';
import { CastItemService } from '@features/game/services/domain/cast-item.service';

@Injectable({
	providedIn: 'root'
})
export class InventoryItemService {
	private readonly cast = inject(CastItemService);

	/** Retourne l'item équipé pour le slot donné, ou null s'il est vide. */
	public findEquipped<T extends ItemInstance>(inventory: Inventory, slot: EquippedSlot): T | null {
		return inventory?.items.find(
			(item) => (item as ItemInstance & { equippedSlot: EquippedSlot }).equippedSlot === slot
		) as T | null;
	}

	/** Vérifie si une munition peut être dropée sur une arme compatible. */
	public isCompatibleAmmoDrop(source: ItemInstance, target: ItemInstance): boolean {
		if (!source || !target) return false;

		const sourceAmmo = this.cast.asAmmoInstance(source);
		const targetWeapon = this.cast.asWeaponInstance(target);
		const targetWeaponItem = this.cast.asWeapon(target?.item);

		if (!sourceAmmo || !targetWeapon || !targetWeaponItem) return false;

		const isCompatible = targetWeaponItem.compatibleAmmo?.some(ammo => ammo.id === sourceAmmo.item.id) ?? false;
		if (!isCompatible) return false;

		// Si la même munition est déjà chargée à pleine capacité → on bloque
		const isSameAmmo = targetWeapon.currentAmmoType?.id === sourceAmmo.item.id;
		const isFull = (targetWeapon.currentAmmoQuantity ?? 0) >= (targetWeaponItem.capacity ?? 0);

		return !(isSameAmmo && isFull);
	}

	public updateItemProperties(
		oldInv: Inventory,
		newInv: Inventory,
		keys: ItemInstanceKeys[]
	): Inventory {
		const updatedItems = newInv.items.map(newItem => {
			const oldItem = oldInv.items.find(i => i.id === newItem.id);

			if (!oldItem) return newItem; // nouvel item ajouté

			const hasChanged = keys.some(
				key => (oldItem as any)[key] !== (newItem as any)[key]
			);

			if (!hasChanged) return oldItem;

			const updates = Object.fromEntries(
				keys.map(key => [key, (newItem as any)[key]])
			);

			return { ...oldItem, ...updates };
		});

		// ici on ignore les items disparus volontairement

		return {
			...oldInv,
			items: updatedItems
		};
	}

}
