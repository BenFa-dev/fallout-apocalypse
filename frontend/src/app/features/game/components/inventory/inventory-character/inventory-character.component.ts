import { CdkDropListGroup } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { LanguageService } from '@core/services/language.service';
import {
	InventorySlotContextMenuComponent
} from '@features/game/components/inventory/inventory-character/inventory-slot-context-menu/inventory-slot-context-menu.component';
import {
	InventorySlotComponent
} from '@features/game/components/inventory/inventory-character/inventory-slot/inventory-slot.component';
import { CharacterStats } from '@features/game/models/character.model';
import {
	ArmorInstance,
	EquippedSlot,
	ItemInstance,
	ItemType,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { ContextMenuStore } from '@features/game/stores/context-menu.store';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
	selector: 'app-inventory-character',
	templateUrl: './inventory-character.component.html',
	styleUrls: ['./inventory-character.component.scss', '../inventory-context-menu.scss'],
	imports: [
		MatCard,
		MatCardContent,
		MatDivider,
		NgOptimizedImage,
		TranslatePipe,
		InventorySlotComponent,
		InventorySlotContextMenuComponent,
		CdkDropListGroup
	]
})
export class InventoryCharacterComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);
	private readonly playerStore = inject(PlayerStore);
	private readonly contextMenuStore = inject(ContextMenuStore);

	// Inputs
	inventoryCurrentWeight: Signal<number> = this.inventoryStore.inventory.currentWeight;
	stats: Signal<CharacterStats | undefined> = this.playerStore.stats;

	// Signals
	armorInstance: Signal<ArmorInstance | null> = this.inventoryStore.armorInstance;
	primaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.primaryWeaponInstance;
	secondaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.secondaryWeaponInstance;

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	showSlotContextMenu(event: MouseEvent, itemInstance: ItemInstance) {
		event.preventDefault();
		this.contextMenuStore.open({
			itemInstance,
			target: event.currentTarget as HTMLElement,
			contextType: 'slot'
		});
	}

	selectItem(item: ItemInstance | null) {
		if (!item) return;
		this.inventoryStore.selectItem(item);
	}
}
