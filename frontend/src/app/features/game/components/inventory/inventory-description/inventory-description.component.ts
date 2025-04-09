import { Component, computed, inject, input } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatDivider } from "@angular/material/divider";
import { MatGridList, MatGridTile } from "@angular/material/grid-list";
import { LanguageService } from '@core/services/language.service';
import { ItemInstance, ItemType, WeaponModeType } from '@features/game/models/inventory/inventory.model';
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
	private readonly asItemPipe = inject(AsItemPipe);
	private readonly languageService = inject(LanguageService);

	protected readonly ItemType = ItemType;

	readonly selectedItemInstance = input<ItemInstance | null>();

	readonly selectedItem = computed(() => this.selectedItemInstance()?.item);
	readonly selectedItemIfWeapon = computed(() => this.asItemPipe.transform(this.selectedItem(), ItemType.WEAPON));
	readonly showShots = computed(() => this.selectedItemIfWeapon()?.weaponModes?.some(mode => mode.modeType === WeaponModeType.BURST) ?? false);
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

}
