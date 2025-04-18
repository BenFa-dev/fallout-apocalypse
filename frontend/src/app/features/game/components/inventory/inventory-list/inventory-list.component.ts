import {
	CdkDrag,
	CdkDragDrop,
	CdkDragEnter,
	CdkDragPlaceholder,
	CdkDragPreview,
	CdkDropList
} from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal, signal } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { LanguageService } from '@core/services/language.service';
import {
	InventoryItemContextMenuComponent
} from '@features/game/components/inventory/inventory-list/inventory-item-context-menu/inventory-item-context-menu.component';
import {
	ArmorInstance,
	DragItem,
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
		CdkDragPlaceholder,
		CdkDragPreview,
		InventoryItemContextMenuComponent
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

	currentDragTarget: Signal<DragItem> = this.inventoryStore.currentDrag.target;
	currentDragSource: Signal<DragItem> = this.inventoryStore.currentDrag.source;

	onItemDropped(event: CdkDragDrop<ItemInstance[]>) {
		const item = event.item.data;
		const slot = event.container;
		console.log("OnItemDropped", item, slot)
	}

	selectItem(item: ItemInstance) {
		this.inventoryStore.selectItem(item);
	}

	showItemContextMenu(event: MouseEvent, item: ItemInstance) {
		event.preventDefault();
		this.contextMenuItem.set(item);
		this.contextMenuPosition.set({ x: event.clientX, y: event.clientY });
		this.showContextMenu.set(true);
	}

	onDropListEntered(event: CdkDragEnter<ItemInstance[]>) {
		const id = event.container.id;
		this.inventoryStore.setCurrentDragTarget(id as EquippedSlot | 'inventory-list', null);
	}

	onDropListExited() {
		this.inventoryStore.setCurrentDragTarget(null, null);
	}

	onItemDragStarted(itemInstance: ItemInstance) {
		this.inventoryStore.setCurrentDragSource("inventory-list", itemInstance);
	}

	onItemDragEnded() {
		this.inventoryStore.setCurrentDragSource(null, null);
		this.inventoryStore.setCurrentDragTarget(null, null);
	}
}
