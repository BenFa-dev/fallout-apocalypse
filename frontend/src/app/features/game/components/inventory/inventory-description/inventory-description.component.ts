import { Component, computed, inject } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatDivider } from "@angular/material/divider";
import { MatGridList, MatGridTile } from "@angular/material/grid-list";
import { LanguageService } from '@core/services/language.service';
import { ItemDetail, ItemType, WeaponModeType } from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { DeepSignal } from "@ngrx/signals";
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

	private readonly asItemPipe = inject(AsItemPipe);
	private readonly languageService = inject(LanguageService);

	private readonly inventoryStore = inject(InventoryStore);

	readonly selectedItemInstance: DeepSignal<ItemDetail> = this.inventoryStore.selectedItem;
	readonly selectedItem = computed(() => this.selectedItemInstance()?.item);
	readonly selectedItemIfWeapon = computed(() => this.asItemPipe.transform(this.selectedItem(), ItemType.WEAPON));
	readonly showShots = computed(() => this.selectedItemIfWeapon()?.weaponModes?.some(mode => mode.modeType === WeaponModeType.BURST) ?? false);
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

}
