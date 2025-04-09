import { CdkDrag, CdkDragDrop, CdkDropList } from '@angular/cdk/drag-drop';
import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, input, output } from '@angular/core';
import { MatCard, MatCardContent } from '@angular/material/card';
import { LanguageService } from '@core/services/language.service';
import { ItemInstance, ItemType } from '@features/game/models/inventory/inventory.model';
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
	private readonly languageService = inject(LanguageService);

	protected readonly ItemType = ItemType;

	// Inputs
	inventoryItems = input.required<ItemInstance[]>();
	armorInstanceId = input<number>();
	primaryWeaponInstanceId = input<number>();
	secondaryWeaponInstanceId = input<number>();

	selectItem = output<ItemInstance>();

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	onItemDropped($event: CdkDragDrop<number>) {
		console.log("To implement", $event)
	}
}
