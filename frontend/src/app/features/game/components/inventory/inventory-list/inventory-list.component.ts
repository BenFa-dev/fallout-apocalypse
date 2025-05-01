import { CdkDrag, CdkDragDrop, CdkDragEnter, CdkDragPreview, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal, signal } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { LanguageService } from '@core/services/language.service';
import {
	InventoryItemContextMenuComponent
} from '@features/game/components/inventory/inventory-list/inventory-item-context-menu/inventory-item-context-menu.component';
import { DragItem, EquippedSlot, ItemInstance, ItemType } from '@features/game/models/inventory/inventory.model';
import { CastItemService } from '@features/game/services/domain/cast-item.service';
import { InventoryItemService } from '@features/game/services/domain/inventory-item.service';
import { ContextMenuStore } from '@features/game/stores/context-menu.store';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslateModule } from '@ngx-translate/core';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-list',
	templateUrl: './inventory-list.component.html',
	styleUrls: ['./inventory-list.component.scss', '../inventory-context-menu.scss'],
	providers: [AsItemPipe, AsItemInstancePipe],
	imports: [
		AsItemInstancePipe,
		CdkDrag,
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
	private readonly contextMenuStore = inject(ContextMenuStore);

	private readonly inventoryItemService = inject(InventoryItemService)
	private readonly castItemService = inject(CastItemService)

	unequippedItems = computed(() =>
		this.inventoryStore.inventory.items()?.filter(i => !('equippedSlot' in i) || i.equippedSlot === null) ?? []);

	selectedItem: Signal<ItemInstance | null> = this.inventoryStore.selectedItem;

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	hoveredTargetItem = signal<ItemInstance | null>(null);

	currentDragSource: Signal<DragItem> = this.inventoryStore.currentDrag.source;

	onItemDropped(event: CdkDragDrop<ItemInstance[]>) {
		const itemInstance = event.item.data;
		const slot = event.container;
		const from = event.previousContainer;
		if (from.id !== 'inventory-list' && slot.id === 'inventory-list' && itemInstance?.equippedSlot !== null) {
			// D'un slot vers la liste, dés-équipement
			this.inventoryStore.unequipItem({ itemInstance });
		} else {
			// D'un item de la liste vers la liste, on ne gère que les ammo -> weapons
			this.onDropItemOnInventoryList(itemInstance);
		}
	}

	onDropItemOnInventoryList(currentDragItem: ItemInstance) {
		if (currentDragItem.item.type !== ItemType.AMMO) return;

		const targetItem = this.hoveredTargetItem();
		if (!targetItem) return;

		const isCompatible = this.inventoryItemService.isCompatibleAmmoDrop(currentDragItem, targetItem);
		if (!isCompatible) return;

		const weaponInstance = this.castItemService.asWeaponInstance(targetItem);
		const ammoInstance = this.castItemService.asAmmoInstance(currentDragItem);
		if (weaponInstance && ammoInstance) {
			this.inventoryStore.loadWeapon({ weaponInstance, ammoInstance });
		}
	}

	selectItem(item: ItemInstance) {
		this.inventoryStore.selectItem(item);
	}

	showItemContextMenu(event: MouseEvent, item: ItemInstance) {
		event.preventDefault();
		this.contextMenuStore.open({
			itemInstance: item,
			target: event.currentTarget as HTMLElement,
			contextType: 'list'
		});
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

	isAvailableDropTarget(): boolean {
		return (this.currentDragSource().slot && this.currentDragSource().slot !== 'inventory-list') ?? false;
	}

	isAmmoTarget(currentItemInstance: ItemInstance): boolean {
		const dragSource = this.currentDragSource().itemInstance;
		return !!dragSource && this.inventoryItemService.isCompatibleAmmoDrop(dragSource, currentItemInstance);
	}
}
