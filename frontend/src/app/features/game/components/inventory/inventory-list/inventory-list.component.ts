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
	DragItem,
	EquippedSlot,
	ItemDetail,
	ItemInstance,
	ItemType
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
		CdkDragPlaceholder,
		CdkDragPreview,
		CdkDropList,
		InventoryItemContextMenuComponent,
		MatCard,
		MatCardContent,
		MatMenuModule,
		NgOptimizedImage,
		TranslateModule,
		AsItemPipe
	]
})
export class InventoryListComponent {
	protected readonly ItemType = ItemType;
	protected readonly EquippedSlot = EquippedSlot;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);

	unequippedItems = computed(() =>
		this.inventoryStore.inventory.items()?.filter(i => !('equippedSlot' in i) || i.equippedSlot === null) ?? []);

	selectedItem: DeepSignal<ItemDetail> = this.inventoryStore.selectedItem;

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	// Context menu
	protected readonly showContextMenu = signal(false);
	protected readonly contextMenuItem = signal<ItemInstance | null>(null);
	protected readonly contextMenuPosition = signal<{ x: number; y: number } | null>(null);

	currentDragTarget: Signal<DragItem> = this.inventoryStore.currentDrag.target;

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
