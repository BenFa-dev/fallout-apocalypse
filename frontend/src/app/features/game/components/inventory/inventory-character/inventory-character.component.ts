import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, input, output, signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { LanguageService } from '@core/services/language.service';
import {
	Armor,
	ArmorInstance,
	EquippedSlot,
	ItemInstance,
	ItemType,
	Weapon,
	WeaponInstance,
	WeaponMode,
	WeaponModeIcons
} from '@features/game/models/inventory/inventory.model';
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { WeaponModeIconPipe } from '@shared/pipes/weapon-mode-icon.pipe';

@Component({
	selector: 'app-inventory-character',
	templateUrl: './inventory-character.component.html',
	styleUrl: './inventory-character.component.scss',
	providers: [AsItemPipe],
	imports: [
		MatCard,
		MatCardContent,
		MatDivider,
		MatCardTitle,
		NgOptimizedImage,
		TranslatePipe,
		WeaponModeIconPipe,
		AsItemPipe
	]
})
export class InventoryCharacterComponent {
	private readonly languageService = inject(LanguageService);

	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;

	// Inputs
	inventoryCurrentWeight = input<number>();
	inventoryMaxWeight = input<number>();

	armorInstance = input<ArmorInstance | null>();
	primaryWeaponInstance = input<WeaponInstance | null>();
	secondaryWeaponInstance = input<WeaponInstance | null>();

	armor = input<Armor | null>();
	primaryWeapon = input<Weapon | null>();
	secondaryWeapon = input<Weapon | null>();

	primaryWeaponMode = input<WeaponMode | null>();
	secondaryWeaponMode = input<WeaponMode | null>();

	// Menu contextuel changement de mode d'arme
	readonly contextMenuPosition = signal<{ x: number; y: number } | null>(null);
	readonly contextMenuSlot = signal<EquippedSlot | null>(null);
	readonly showContextMenu = signal(false);

	// Events
	changeWeaponModeEvent = output<{ mode: WeaponMode; equippedSlot: EquippedSlot }>();
	selectItemEvent = output<ItemInstance | null | undefined>();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	showWeaponContextMenu(event: MouseEvent, slot: EquippedSlot) {
		event.preventDefault();
		if (this.contextMenuSlot() !== slot || !this.showContextMenu()) {
			this.contextMenuPosition.set({ x: event.clientX, y: event.clientY });
			this.contextMenuSlot.set(slot);
			this.showContextMenu.set(true);
		}
	}

	changeWeaponMode(mode: WeaponMode, equippedSlot: EquippedSlot) {
		this.showContextMenu.set(false);
		this.changeWeaponModeEvent.emit({ mode, equippedSlot });
	}

	protected readonly WeaponModeIcons = WeaponModeIcons;
}
