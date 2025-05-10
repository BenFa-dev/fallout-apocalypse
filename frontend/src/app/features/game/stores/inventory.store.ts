import { computed, inject } from '@angular/core';
import {
	AmmoInstance,
	ArmorInstance,
	DragItem,
	EquippedSlot,
	Inventory,
	ItemInstance,
	WeaponInstance,
	WeaponMode
} from '@features/game/models/inventory/inventory.model';
import { InventoryService } from '@features/game/services/api/inventory.service';
import { InventoryItemService } from '@features/game/services/domain/inventory-item.service';
import { PlayerStore } from '@features/game/stores/player.store';
import { patchState, signalStore, withComputed, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type InventoryStore = InstanceType<typeof InventoryStore>;

type InventoryState = {
	isInitialized: boolean;
	isOpen: boolean;
	selectedItem: ItemInstance | null;
	isLoading: boolean;
	inventory: Inventory;
	currentDrag: {
		source: DragItem,
		target: DragItem
	}
};

const initialState: InventoryState = {
	isInitialized: false,
	isOpen: false,
	selectedItem: null,
	isLoading: false,
	inventory: {
		id: 0,
		characterId: 0,
		currentWeight: 0,
		items: []
	},
	currentDrag: {
		source: { slot: null, itemInstance: null },
		target: { slot: null, itemInstance: null }
	}
};

export const InventoryStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withComputed((store, inventoryItemService = inject(InventoryItemService)) => ({
		primaryWeaponInstance: computed(() => inventoryItemService.findEquipped<WeaponInstance>(store.inventory(), EquippedSlot.PRIMARY_WEAPON)),
		secondaryWeaponInstance: computed(() => inventoryItemService.findEquipped<WeaponInstance>(store.inventory(), EquippedSlot.SECONDARY_WEAPON)),
		armorInstance: computed(() => inventoryItemService.findEquipped<ArmorInstance>(store.inventory(), EquippedSlot.ARMOR))
	})),
	withMethods((
		store,
		inventoryService = inject(InventoryService),
		inventoryItemService = inject(InventoryItemService),
		playerStore = inject(PlayerStore)
	) => {

		return {
			open: () => patchState(store, { isOpen: true }),

			close: () => patchState(store, { isOpen: false, selectedItem: null }),

			selectItem: (itemInstance: ItemInstance) => patchState(store, { selectedItem: itemInstance }),

			loadInventory: rxMethod<void>(
				pipe(
					tap(() => patchState(store, { isLoading: true })),
					switchMap(() =>
						inventoryService.getCharacterInventory().pipe(
							tap({
								next: (character) => {
									console.log('🎒 Inventaire chargé');
									patchState(store, {
										inventory: character.inventory,
										isLoading: false,
										isInitialized: true
									})
									playerStore.updatePlayerState({
										currentStats: character.currentStats,
										perkInstances: character.perks,
										skillInstances: character.skills,
										specialInstances: character.specials,
										stats: character?.stats
									});
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
								next: (character) => {
									console.log('🎒 Equipement');
									patchState(store, {
										inventory: inventoryItemService.updateItemProperties(store.inventory(), character.inventory, ['equippedSlot'])
									});
									playerStore.updatePlayerState({
										stats: character?.stats,
										specialInstances: character.specials
									});
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
								next: (character) => {
									console.log('🎒 Dés-équipement');
									patchState(store, {
										inventory: inventoryItemService.updateItemProperties(store.inventory(), character.inventory, ['equippedSlot'])
									})
									playerStore.updatePlayerState({
										stats: character?.stats,
										specialInstances: character.specials
									});
								},
								error: (error) => {
									console.error('❌ Erreur lors du dés-équipement:', error);
								}
							})
						)
					)
				)
			),

			changeWeaponMode: rxMethod<{ mode: WeaponMode, weaponInstance: WeaponInstance }>(
				pipe(
					switchMap(({ mode, weaponInstance }) =>
						inventoryService.changeWeaponMode(weaponInstance.id, mode.id).pipe(
							tap({
								next: (character) => {
									console.log('🎒 Changement de mode');
									patchState(store, {
										inventory: inventoryItemService.updateItemProperties(store.inventory(), character.inventory, ['currentWeaponMode'])
									})
								},
								error: (error) => {
									console.error('❌ Erreur lors du changement de mode:', error);
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
								next: (character) => {
									console.log('🎒 Chargement');
									patchState(store, {
										inventory: inventoryItemService.updateItemProperties(store.inventory(), character.inventory, ['currentAmmoQuantity', 'quantity'])
									})
									playerStore.updatePlayerState({
										stats: character?.stats,
										specialInstances: character.specials
									});
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
								next: (character) => {
									console.log('🎒 Déchargement');
									patchState(store, {
										inventory: inventoryItemService.updateItemProperties(store.inventory(), character.inventory, ['currentAmmoQuantity', 'quantity'])
									})
									playerStore.updatePlayerState({
										stats: character?.stats,
										specialInstances: character.specials
									});
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
