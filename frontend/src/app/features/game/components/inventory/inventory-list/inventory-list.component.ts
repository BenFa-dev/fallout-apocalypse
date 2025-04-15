import { CdkDrag, CdkDragDrop, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal, signal } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { LanguageService } from '@core/services/language.service';
import {
	AmmoInstance,
	ArmorInstance,
	EquippedSlot,
	ItemDetail,
	ItemInstance,
	ItemType,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { DeepSignal } from '@ngrx/signals';
import { TranslateModule } from '@ngx-translate/core';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-list',
	templateUrl: './inventory-list.component.html',
	styleUrls: ['./inventory-list.component.scss', '../inventory-context-menu.scss'],
	providers: [AsItemPipe],
	imports: [
		AsItemInstancePipe,
		CdkDrag,
		CdkDropList,
		MatCard,
		MatCardContent,
		NgOptimizedImage,
		TranslateModule,
		MatMenuModule,
		MatIcon
	]
})
export class InventoryListComponent {
	protected readonly ItemType = ItemType;
	protected readonly EquippedSlot = EquippedSlot;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);

	inventoryItems: Signal<ItemInstance[]> = this.inventoryStore.inventory.items;
	armorInstance: Signal<ArmorInstance | null> = this.inventoryStore.armor.instance;
	primaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.primaryWeapon.instance;
	secondaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.secondaryWeapon.instance;
	selectedItem: DeepSignal<ItemDetail> = this.inventoryStore.selectedItem;

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	// Context menu
	protected readonly showContextMenu = signal(false);
	protected readonly contextMenuItem = signal<ItemInstance | null>(null);
	protected readonly contextMenuPosition = signal<{ x: number; y: number } | null>(null);

	onItemDropped($event: CdkDragDrop<number>) {
		console.log("To implement", $event)
	}

	selectItem(item: ItemInstance) {
		this.inventoryStore.selectItem(item);
	}

	equipItem(itemInstance: WeaponInstance | ArmorInstance | null, slot: EquippedSlot) {
		if (!itemInstance) return
		this.inventoryStore.equipItem({ itemInstance, targetedSlot: slot });
		this.showContextMenu.set(false);
	}

	loadWeapon(weaponInstance: WeaponInstance | null, ammoInstance: AmmoInstance | null) {
		if (!weaponInstance || !ammoInstance) return
		this.inventoryStore.loadWeapon({ weaponInstance, ammoInstance });
		this.showContextMenu.set(false);
	}

	showItemContextMenu(event: MouseEvent, item: ItemInstance) {
		event.preventDefault();
		this.contextMenuItem.set(item);
		this.contextMenuPosition.set({ x: event.clientX, y: event.clientY });
		this.showContextMenu.set(true);
	}
}
