import { CdkDrag, CdkDragDrop, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { LanguageService } from '@core/services/language.service';
import { ArmorInstance, ItemInstance, ItemType, WeaponInstance } from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslateModule } from '@ngx-translate/core';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-list',
	templateUrl: './inventory-list.component.html',
	styleUrl: './inventory-list.component.scss',
	providers: [AsItemPipe],
	imports: [
		AsItemInstancePipe,
		CdkDrag,
		CdkDropList,
		MatCard,
		MatCardContent,
		NgOptimizedImage,
		TranslateModule
	]
})
export class InventoryListComponent {
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);

	private readonly inventoryStore = inject(InventoryStore);

	inventoryItems: Signal<ItemInstance[]> = this.inventoryStore.inventory.items;
	armorInstance: Signal<ArmorInstance | null> = this.inventoryStore.armor.instance;
	primaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.primaryWeapon.instance;
	secondaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.secondaryWeapon.instance;

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	onItemDropped($event: CdkDragDrop<number>) {
		console.log("To implement", $event)
	}

	selectItem(item: ItemInstance) {
		this.inventoryStore.selectItem(item);
	}
}
