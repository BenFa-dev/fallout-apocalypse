import { inject } from '@angular/core';
import {
	ArmorDetail,
	ArmorInstance,
	EquippedSlot,
	Inventory,
	ItemDetail,
	ItemInstance,
	ItemType,
	WeaponDetail,
	WeaponInstance,
	WeaponMode
} from '@features/game/models/inventory/inventory.model';
import { InventoryService } from '@features/game/services/api/inventory.service';
import { InventoryItemService } from '@features/game/services/domain/inventory-item.service';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { pipe, tap } from 'rxjs';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type InventoryStore = InstanceType<typeof InventoryStore>;

type InventoryState = {
	isInitialized: boolean
	isOpen: boolean;
	selectedItem: ItemDetail;
	isLoading: boolean;
	inventory: Inventory;
	primaryWeapon: WeaponDetail,
	secondaryWeapon: WeaponDetail,
	armor: ArmorDetail
};

const initialState: InventoryState = {
	isInitialized: false,
	isOpen: false,
	selectedItem: {
		instance: null,
		item: null
	},
	isLoading: false,
	inventory: {
		id: 0,
		characterId: 0,
		currentWeight: 0,
		maxWeight: 0,
		items: []
	},
	primaryWeapon: {
		instance: null,
		item: null,
		mode: null
	},
	secondaryWeapon: {
		instance: null,
		item: null,
		mode: null
	},
	armor: {
		instance: null,
		item: null
	}
};

export const InventoryStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withMethods((
		store,
		inventoryService = inject(InventoryService),
		inventoryItemService = inject(InventoryItemService),
		asItemPipe = inject(AsItemPipe)
	) => ({
		open() {
			patchState(store, { isOpen: true });
		},
		close() {
			patchState(store, { isOpen: false, selectedItem: { instance: null, item: null } });
		},
		selectItem(itemInstance: ItemInstance) {
			patchState(store, { selectedItem: { item: itemInstance?.item, instance: itemInstance } });
		},
		changeWeaponMode(event: { mode: WeaponMode; equippedSlot: EquippedSlot }) {
			if (event.equippedSlot === EquippedSlot.PRIMARY_WEAPON) {
				patchState(store, { primaryWeapon: { ...store.primaryWeapon(), mode: event.mode } });
			} else if (event.equippedSlot === EquippedSlot.SECONDARY_WEAPON) {
				patchState(store, { secondaryWeapon: { ...store.secondaryWeapon(), mode: event.mode } });
			}
		},
		loadInventory: rxMethod<void>(
			pipe(
				tap(() => patchState(store, { isLoading: true })),
				tap({
					next: () => {
						inventoryService.getInventory().subscribe({
							next: (inventory) => {
								console.log('🎒 Inventaire chargé');

								const armorInstance = inventoryItemService.findEquipped<ArmorInstance>(inventory, EquippedSlot.ARMOR);
								const primaryWeaponInstance = inventoryItemService.findEquipped<WeaponInstance>(inventory, EquippedSlot.PRIMARY_WEAPON);
								const secondaryWeaponInstance = inventoryItemService.findEquipped<WeaponInstance>(inventory, EquippedSlot.SECONDARY_WEAPON);

								const primaryWeapon = asItemPipe.transform(primaryWeaponInstance?.item, ItemType.WEAPON);
								const secondaryWeapon = asItemPipe.transform(secondaryWeaponInstance?.item, ItemType.WEAPON);
								const armor = asItemPipe.transform(armorInstance?.item, ItemType.ARMOR);

								const primaryWeaponMode = primaryWeapon?.weaponModes[0];
								const secondaryWeaponMode = primaryWeapon?.weaponModes[0];

								patchState(store,
									{
										inventory,
										primaryWeapon: {
											instance: primaryWeaponInstance,
											item: primaryWeapon,
											mode: primaryWeaponMode
										},
										secondaryWeapon: {
											instance: secondaryWeaponInstance,
											item: secondaryWeapon,
											mode: secondaryWeaponMode
										},
										armor: { instance: armorInstance, item: armor },
										isLoading: false,
										isInitialized: true
									}
								);
							},
							error: (error) => {
								console.error('❌ Erreur lors du chargement de l\'inventaire:', error);
								patchState(store, { isLoading: false });
							}
						});
					}
				})
			)
		)
	})),
	withHooks({
		onInit(store) {
			store.loadInventory();
		}
	})
);

