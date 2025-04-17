import { CdkDrag, CdkDragEnter, CdkDragPlaceholder, CdkDragPreview, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, input, Signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { LanguageService } from '@core/services/language.service';
import { EquippedSlot, ItemDetail, ItemInstance, ItemType } from '@features/game/models/inventory/inventory.model';
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
	currentDragSource: Signal<EquippedSlot | 'inventory-list' | null> = this.inventoryStore.currentDrag.source;
	currentDragTarget: Signal<EquippedSlot | 'inventory-list' | null> = this.inventoryStore.currentDrag.target;

	onDropListEntered(event: CdkDragEnter<ItemInstance>) {
		const id = event.container.id;
		if (id === 'inventory-list' || Object.values(EquippedSlot).includes(id as EquippedSlot)) {
			this.inventoryStore.setCurrentDragTarget(id as EquippedSlot | 'inventory-list');
		}
	}

	onDropListExited() {
		this.inventoryStore.setCurrentDragTarget(null);
	}

	onItemDragStarted(equippedSlot: EquippedSlot) {
		this.inventoryStore.setCurrentDragSource(equippedSlot);
	}

	onItemDragEnded() {
		this.inventoryStore.setCurrentDragSource(null);
		this.inventoryStore.setCurrentDragTarget(null);
	}

	canDropInSlot(slot: EquippedSlot) {
		return (drag: CdkDrag): boolean => {
			const item = drag.data as ItemInstance;
			if (!item?.item) return false;
			const type = item.item.type;
			if (slot === EquippedSlot.ARMOR) return type === ItemType.ARMOR;
			if (slot === EquippedSlot.PRIMARY_WEAPON || slot === EquippedSlot.SECONDARY_WEAPON)
				return type === ItemType.WEAPON;

			return false;
		};
	}

}
