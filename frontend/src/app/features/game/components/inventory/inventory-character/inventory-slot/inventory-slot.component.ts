import { CdkDrag, CdkDragEnter, CdkDragPlaceholder, CdkDragPreview, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, input, Signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { LanguageService } from '@core/services/language.service';
import {
	DragItem,
	EquippedSlot,
	ItemDetail,
	ItemInstance,
	ItemType
} from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemDetailPipe } from '@shared/pipes/as-item-detail.pipe';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { WeaponModeIconPipe } from '@shared/pipes/weapon-mode-icon.pipe';

@Component({
	selector: 'app-inventory-slot',
	templateUrl: './inventory-slot.component.html',
	styleUrls: ['./inventory-slot.component.scss', '../../inventory-context-menu.scss'],
	providers: [AsItemPipe],
	imports: [
		MatCard,
		MatCardContent,
		MatCardTitle,
		NgOptimizedImage,
		TranslatePipe,
		CdkDropList,
		CdkDrag,
		CdkDragPreview,
		CdkDragPlaceholder,
		AsItemPipe,
		AsItemInstancePipe,
		WeaponModeIconPipe,
		AsItemDetailPipe
	]
})
export class InventorySlotComponent {
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);

	// Inputs
	readonly slot = input<EquippedSlot>();
	readonly itemDetail = input<ItemDetail | null>();
	readonly label = input<string>();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	// Signals
	currentDragSource: Signal<DragItem> = this.inventoryStore.currentDrag.source;
	currentDragTarget: Signal<DragItem> = this.inventoryStore.currentDrag.target;

	onDropListEntered(event: CdkDragEnter<ItemInstance>) {
		const id = event.container.id;
		this.inventoryStore.setCurrentDragTarget(id as EquippedSlot | 'inventory-list', event.item.data);
	}

	onDropListExited() {
		this.inventoryStore.setCurrentDragTarget(null, null);
	}

	onItemDragStarted(equippedSlot: EquippedSlot, itemInstance: ItemInstance) {
		this.inventoryStore.setCurrentDragSource(equippedSlot, itemInstance);
	}

	onItemDragEnded() {
		this.inventoryStore.setCurrentDragSource(null, null);
		this.inventoryStore.setCurrentDragTarget(null, null);
	}

	get hideSlot(): boolean {
		const { source, target } = this.inventoryStore.currentDrag();
		return (
			source.slot === 'inventory-list' &&
			target.slot === this.slot() &&
			!this.isAmmoDragged
		);
	}

	get isAmmoDragged(): boolean {
		return this.currentDragSource()?.itemInstance?.item?.type === ItemType.AMMO;
	}

	public getWeaponDetail = (slot: EquippedSlot) =>
		slot === EquippedSlot.PRIMARY_WEAPON
			? this.inventoryStore.primaryWeapon()
			: slot === EquippedSlot.SECONDARY_WEAPON
				? this.inventoryStore.secondaryWeapon()
				: { item: null, instance: null, mode: null };

	public canDropItemOnSlot(item: ItemInstance | null, slot: EquippedSlot): boolean {
		const type = item?.item?.type;
		if (!type) return false;

		if (slot === EquippedSlot.ARMOR) return type === ItemType.ARMOR;

		if (slot === EquippedSlot.PRIMARY_WEAPON || slot === EquippedSlot.SECONDARY_WEAPON) {
			if (type === ItemType.WEAPON) return true;

			if (type === ItemType.AMMO) {
				const weapon = this.getWeaponDetail(slot).item;
				if (!weapon || weapon.type !== ItemType.WEAPON) return false;
				return weapon.compatibleAmmo?.some(ammo => ammo.id === item.item.id) ?? false;
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
