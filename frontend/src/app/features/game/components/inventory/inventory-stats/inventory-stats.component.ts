import { Component, computed, inject, input } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { MatTooltip } from '@angular/material/tooltip';
import { LanguageService } from '@core/services/language.service';
import { Character } from '@features/game/models/character.model';
import {
	Armor,
	ArmorInstance,
	ItemType,
	Weapon,
	WeaponInstance,
	WeaponMode
} from '@features/game/models/inventory/inventory.model';
import { TranslatePipe } from '@ngx-translate/core';
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
		WeaponModeIconPipe
	]
})
export class InventoryStatsComponent {
	private readonly languageService = inject(LanguageService);
	
	protected readonly ItemType = ItemType;

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	// Inputs
	player = input<Character | null>();

	armorInstance = input<ArmorInstance | null>();
	primaryWeaponInstance = input<WeaponInstance | null>();
	secondaryWeaponInstance = input<WeaponInstance | null>();

	armor = input<Armor | null>();
	primaryWeapon = input<Weapon | null>();
	secondaryWeapon = input<Weapon | null>();

	primaryWeaponMode = input<WeaponMode | null>();
	secondaryWeaponMode = input<WeaponMode | null>();
}
