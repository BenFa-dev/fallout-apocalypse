import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal, signal } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { LanguageService } from '@core/services/language.service';
import {
	InventorySlotContextMenuComponent
} from '@features/game/components/inventory/inventory-character/inventory-slot-context-menu/inventory-slot-context-menu.component';
import {
	InventorySlotComponent
} from '@features/game/components/inventory/inventory-character/inventory-slot/inventory-slot.component';
import {
	ArmorDetail,
	EquippedSlot,
	ItemDetail,
	ItemInstance,
	ItemType,
	WeaponDetail
} from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-character',
	templateUrl: './inventory-character.component.html',
	styleUrls: ['./inventory-character.component.scss', '../inventory-context-menu.scss'],
	providers: [AsItemPipe],
	imports: [
		MatCard,
		MatCardContent,
		MatDivider,
		NgOptimizedImage,
		TranslatePipe,
		InventorySlotComponent,
		InventorySlotContextMenuComponent
	]
})
export class InventoryCharacterComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);

	// Inputs
	inventoryCurrentWeight: Signal<number> = this.inventoryStore.inventory.currentWeight;
	inventoryMaxWeight: Signal<number> = this.inventoryStore.inventory.maxWeight;

	// Signals
	armor: Signal<ArmorDetail> = this.inventoryStore.armor;
	primaryWeapon: Signal<WeaponDetail> = this.inventoryStore.primaryWeapon;
	secondaryWeapon: Signal<WeaponDetail> = this.inventoryStore.secondaryWeapon;

	// Menu contextuel changement de mode d'arme
	readonly contextMenuPosition = signal<{ x: number; y: number } | null>(null);
	readonly contextMenuSlot = signal<EquippedSlot | null>(null);
	readonly contextMenuItemDetail = signal<ItemDetail | null>(null);
	readonly showContextMenu = signal(false);

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	showSlotContextMenu(event: MouseEvent, slot: EquippedSlot, itemDetail: ItemDetail) {
		event.preventDefault();
		if (this.contextMenuSlot() !== slot || !this.showContextMenu()) {
			this.contextMenuPosition.set({ x: event.clientX, y: event.clientY });
			this.contextMenuSlot.set(slot);
			this.contextMenuItemDetail.set(itemDetail);
			this.showContextMenu.set(true);
		}
	}

	selectItem(item: ItemInstance | null) {
		if (!item) return;
		this.inventoryStore.selectItem(item);
	}
}
