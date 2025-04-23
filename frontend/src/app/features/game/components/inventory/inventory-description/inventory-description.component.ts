import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatDivider } from "@angular/material/divider";
import { MatGridList, MatGridTile } from "@angular/material/grid-list";
import { LanguageService } from '@core/services/language.service';
import { ItemInstance, ItemType, WeaponModeType } from '@features/game/models/inventory/inventory.model';
import { CastItemService } from '@features/game/services/domain/cast-item.service';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemInstancePipe } from "@shared/pipes/as-item-instance.pipe";
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-description',
	templateUrl: './inventory-description.component.html',
	styleUrl: './inventory-description.component.scss',
	providers: [],
	imports: [
		MatCard,
		MatCardHeader,
		MatCardContent,
		MatCardTitle,
		TranslatePipe,
		AsItemPipe,
		MatGridList,
		MatGridTile,
		MatDivider,
		AsItemInstancePipe
	]
})
export class InventoryDescriptionComponent {
	protected readonly ItemType = ItemType;

	private readonly castItemService = inject(CastItemService);
	private readonly languageService = inject(LanguageService);

	private readonly inventoryStore = inject(InventoryStore);

	readonly selectedItemInstance: Signal<ItemInstance | null> = this.inventoryStore.selectedItem;
	readonly selectedItem = computed(() => this.selectedItemInstance()?.item);
	readonly selectedItemIfWeapon = computed(() => this.castItemService.asWeapon(this.selectedItem()));
	readonly showShots = computed(() => this.selectedItemIfWeapon()?.weaponModes?.some(mode => mode.modeType === WeaponModeType.BURST) ?? false);
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

}
