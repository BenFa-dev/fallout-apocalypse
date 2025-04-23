import { CdkDrag, CdkDragDrop, CdkDragEnter, CdkDragPreview, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, input, Signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { LanguageService } from '@core/services/language.service';
import { DragItem, EquippedSlot, ItemInstance, ItemType } from '@features/game/models/inventory/inventory.model';
import { InventoryItemService } from '@features/game/services/domain/inventory-item.service';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { WeaponModeIconPipe } from '@shared/pipes/weapon-mode-icon.pipe';

@Component({
	selector: 'app-inventory-slot',
	templateUrl: './inventory-slot.component.html',
	styleUrls: ['./inventory-slot.component.scss', '../../inventory-context-menu.scss'],
	providers: [AsItemPipe, AsItemInstancePipe],
	imports: [
		MatCard,
		MatCardContent,
		MatCardTitle,
		NgOptimizedImage,
		TranslatePipe,
		CdkDropList,
		CdkDrag,
		CdkDragPreview,
		AsItemPipe,
		AsItemInstancePipe,
		WeaponModeIconPipe
	]
})
export class InventorySlotComponent {
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);
	private readonly inventoryItemService = inject(InventoryItemService)

	// Inputs
	readonly slot = input<EquippedSlot>();
	readonly itemInstance = input<ItemInstance | null>();
	readonly label = input<string>();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	// Signals
	currentDragSource: Signal<DragItem> = this.inventoryStore.currentDrag.source;

	onItemDropped(event: CdkDragDrop<ItemInstance>) {
		const from = event.previousContainer.id;
		const to = event.container.id;
		const newItemInstance = event.item.data;

		if (to === 'inventory-list') {
			this.inventoryStore.unequipItem({ itemInstance: newItemInstance });
		} else if (from !== to) {
			if (newItemInstance?.item?.type === ItemType.AMMO) {
				const weaponInstance = this.getTargetWeaponInstance(to as EquippedSlot);
				if (!weaponInstance) return;
				this.inventoryStore.loadWeapon({ ammoInstance: newItemInstance, weaponInstance });
			} else {
				this.inventoryStore.equipItem({ itemInstance: newItemInstance, targetedSlot: to as EquippedSlot });
			}

		}
	}

	onDropListEntered(event: CdkDragEnter<ItemInstance>) {
		const id = event.container.id;
		this.inventoryStore.setCurrentDragTarget(id as EquippedSlot | 'inventory-list', event.item.data);
	}

	onDropListExited() {
		this.inventoryStore.setCurrentDragTarget(null, null);
	}

	onItemDragStarted(equippedSlot: EquippedSlot, itemInstance?: ItemInstance | null) {
		if (!itemInstance) return;
		this.inventoryStore.setCurrentDragSource(equippedSlot, itemInstance);
	}

	onItemDragEnded() {
		this.inventoryStore.setCurrentDragSource(null, null);
		this.inventoryStore.setCurrentDragTarget(null, null);
	}

	public getTargetWeaponInstance = (slot: EquippedSlot) =>
		slot === EquippedSlot.PRIMARY_WEAPON ?
			this.inventoryStore.primaryWeaponInstance() :
			slot === EquippedSlot.SECONDARY_WEAPON ? this.inventoryStore.secondaryWeaponInstance() : null;

	public canDropItemOnSlot(item: ItemInstance | null, slot: EquippedSlot): boolean {
		const type = item?.item?.type;
		if (!type) return false;

		if (slot === EquippedSlot.ARMOR) return type === ItemType.ARMOR;

		if ([EquippedSlot.PRIMARY_WEAPON, EquippedSlot.SECONDARY_WEAPON].includes(slot)) {
			if (item.item.type === ItemType.WEAPON) return true;

			if (item.item.type === ItemType.AMMO) {
				const weaponInstance = this.getTargetWeaponInstance(slot);
				if (!weaponInstance) return false;
				return this.inventoryItemService.isCompatibleAmmoDrop(item, weaponInstance);
			}
		}

		return false;
	}

	canDropInSlot(slot: EquippedSlot) {
		return ({ data }: CdkDrag<ItemInstance>): boolean =>
			this.canDropItemOnSlot(data, slot);
	}

	public isAvailableDropTarget(slot: EquippedSlot): boolean {
		const item = this.currentDragSource()?.itemInstance;
		return this.canDropItemOnSlot(item, slot);
	}
}
