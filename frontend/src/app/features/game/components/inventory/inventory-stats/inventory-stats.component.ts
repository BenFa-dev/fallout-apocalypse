import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { MatTooltip } from '@angular/material/tooltip';
import { LanguageService } from '@core/services/language.service';
import { Character } from "@features/game/models/character.model";
import { ArmorInstance, ItemType, WeaponInstance } from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { WeaponModeIconPipe } from '@shared/pipes/weapon-mode-icon.pipe';

@Component({
	selector: 'app-inventory-stats',
	templateUrl: './inventory-stats.component.html',
	styleUrl: './inventory-stats.component.scss',
	imports: [
		MatCard,
		MatCardTitle,
		MatCardContent,
		MatCardHeader,
		TranslatePipe,
		MatTooltip,
		MatGridTile,
		MatGridList,
		MatDivider,
		WeaponModeIconPipe,
		AsItemPipe
	]
})
export class InventoryStatsComponent {
	private readonly languageService = inject(LanguageService);

	protected readonly ItemType = ItemType;

	private readonly playerStore = inject(PlayerStore);
	private readonly inventoryStore = inject(InventoryStore);

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	player: Signal<Character | null> = this.playerStore.player;

	primaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.primaryWeaponInstance;
	secondaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.secondaryWeaponInstance;
	armorInstance: Signal<ArmorInstance | null> = this.inventoryStore.armorInstance;

	readonly displayedDamages = computed(() =>
		(this.player()?.stats?.damages ?? [])
			.filter(d => d.damageType.visible)
			.sort((a, b) => a.damageType.displayOrder - b.damageType.displayOrder)
	);

}
