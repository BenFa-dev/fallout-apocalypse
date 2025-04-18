import { inject } from '@angular/core';
import {
	AmmoInstance,
	ArmorDetail,
	ArmorInstance,
	DragItem,
	EquippedSlot,
	Inventory,
	ItemDetail,
	ItemInstance,
	WeaponDetail,
	WeaponInstance,
	WeaponMode
} from '@features/game/models/inventory/inventory.model';
import { InventoryService } from '@features/game/services/api/inventory.service';
import { InventoryItemService } from '@features/game/services/domain/inventory-item.service';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type InventoryStore = InstanceType<typeof InventoryStore>;

type InventoryState = {
	isInitialized: boolean;
	isOpen: boolean;
	selectedItem: ItemDetail;
	isLoading: boolean;
	inventory: Inventory;
	primaryWeapon: WeaponDetail;
	secondaryWeapon: WeaponDetail;
	armor: ArmorDetail;
	currentDrag: {
		source: DragItem,
		target: DragItem
	}
};

const initialState: InventoryState = {
	isInitialized: false,
	isOpen: false,
	selectedItem: { instance: null, item: null },
	isLoading: false,
	inventory: {
		id: 0,
		characterId: 0,
		currentWeight: 0,
		maxWeight: 0,
		items: []
	},
	primaryWeapon: { instance: null, item: null, mode: null },
	secondaryWeapon: { instance: null, item: null, mode: null },
	armor: { instance: null, item: null },
	currentDrag: {
		source: { slot: null, itemInstance: null },
		target: { slot: null, itemInstance: null }
	}
};

export const InventoryStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withMethods((
		store,
		inventoryService = inject(InventoryService),
		inventoryItemService = inject(InventoryItemService)
	) => {
		const applyInventoryUpdate = (inventory: Inventory) => {
			const {
				armorInstance,
				primaryWeaponInstance,
				secondaryWeaponInstance,
				primaryWeapon,
				secondaryWeapon,
				armor,
				primaryWeaponMode,
				secondaryWeaponMode
			} = inventoryItemService.getEquippedItems(inventory);

			if (!inventory) return;

			patchState(store, {
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
				armor: {
					instance: armorInstance,
					item: armor
				},
				isLoading: false,
				isInitialized: true
			});
		};

		return {
			open: () => patchState(store, { isOpen: true }),

			close: () => patchState(store, {
				isOpen: false,
				selectedItem: { instance: null, item: null }
			}),

			selectItem: (itemInstance: ItemInstance) =>
				patchState(store, {
					selectedItem: { instance: itemInstance, item: itemInstance?.item }
				}),

			changeWeaponMode: ({ mode, equippedSlot }: { mode: WeaponMode, equippedSlot: EquippedSlot }) => {
				if (equippedSlot === EquippedSlot.PRIMARY_WEAPON) {
					patchState(store, {
						primaryWeapon: { ...store.primaryWeapon(), mode }
					});
				} else if (equippedSlot === EquippedSlot.SECONDARY_WEAPON) {
					patchState(store, {
						secondaryWeapon: { ...store.secondaryWeapon(), mode }
					});
				}
			},

			loadInventory: rxMethod<void>(
				pipe(
					tap(() => patchState(store, { isLoading: true })),
					switchMap(() =>
						inventoryService.getInventory().pipe(
							tap({
								next: (inventory) => {
									console.log('🎒 Inventaire chargé');
									applyInventoryUpdate(inventory);
								},
								error: (error) => {
									console.error('❌ Erreur lors du chargement de l\'inventaire:', error);
									patchState(store, { isLoading: false });
								}
							})
						)
					)
				)
			),

			equipItem: rxMethod<{ itemInstance: WeaponInstance | ArmorInstance, targetedSlot: EquippedSlot }>(
				pipe(
					switchMap(({ itemInstance, targetedSlot }) =>
						inventoryService.equipItem(itemInstance.id, targetedSlot).pipe(
							tap({
								next: (inventory) => {
									console.log('🎒 Equipement');
									applyInventoryUpdate(inventory)
								},
								error: (error) => {
									console.error('❌ Erreur lors de l\'équipement:', error);
								}
							})
						)
					)
				)
			),

			unequipItem: rxMethod<{ itemInstance: ItemInstance }>(
				pipe(
					switchMap(({ itemInstance }) =>
						inventoryService.unequipItem(itemInstance.id).pipe(
							tap({
								next: (inventory) => {
									console.log('🎒 Déséquipement');
									applyInventoryUpdate(inventory)
								},
								error: (error) => {
									console.error('❌ Erreur lors du déséquipement:', error);
								}
							})
						)
					)
				)
			),

			loadWeapon: rxMethod<{ weaponInstance: WeaponInstance, ammoInstance: AmmoInstance }>(
				pipe(
					switchMap(({ weaponInstance, ammoInstance }) =>
						inventoryService.loadWeapon(weaponInstance.id, ammoInstance.id).pipe(
							tap({
								next: (inventory) => {
									console.log('🎒 Chargement');
									applyInventoryUpdate(inventory)
								},
								error: (error) => {
									console.error('❌ Erreur lors du chargement:', error);
								}
							})
						)
					)
				)
			),

			unloadWeapon: rxMethod<{ weaponInstance: WeaponInstance }>(
				pipe(
					switchMap(({ weaponInstance }) =>
						inventoryService.unloadWeapon(weaponInstance.id).pipe(
							tap({
								next: (inventory) => {
									console.log('🎒 Déchargement');
									applyInventoryUpdate(inventory)
								},
								error: (error) => {
									console.error('❌ Erreur lors du déchargement:', error);
								}
							})
						)
					)
				)
			),
			setCurrentDragTarget: (slot: EquippedSlot | 'inventory-list' | null, itemInstance: ItemInstance | null) => patchState(store, {
				currentDrag: {
					...store.currentDrag(),
					target: { slot, itemInstance }
				}
			}),
			setCurrentDragSource: (slot: EquippedSlot | 'inventory-list' | null, itemInstance: ItemInstance | null) => patchState(store, {
				currentDrag: {
					...store.currentDrag(),
					source: { slot, itemInstance }
				}
			})

		};
	}),
	withHooks({
		onInit(store) {
			store.loadInventory();
		}
	})
);
