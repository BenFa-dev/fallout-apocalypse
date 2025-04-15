import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal, signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { LanguageService } from '@core/services/language.service';
import {
	ArmorDetail,
	ArmorInstance,
	EquippedSlot,
	ItemInstance,
	ItemType,
	WeaponDetail,
	WeaponInstance,
	WeaponMode,
	WeaponModeIcons
} from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { WeaponModeIconPipe } from '@shared/pipes/weapon-mode-icon.pipe';

@Component({
	selector: 'app-inventory-character',
	templateUrl: './inventory-character.component.html',
	styleUrls: ['./inventory-character.component.scss', '../inventory-context-menu.scss'],
	providers: [AsItemPipe],
	imports: [
		MatCard,
		MatCardContent,
		MatDivider,
		MatCardTitle,
		NgOptimizedImage,
		TranslatePipe,
		WeaponModeIconPipe
	]
})
export class InventoryCharacterComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;
	protected readonly WeaponModeIcons = WeaponModeIcons;

	private readonly languageService = inject(LanguageService);

	private readonly inventoryStore = inject(InventoryStore);

	// Inputs
	inventoryCurrentWeight: Signal<number> = this.inventoryStore.inventory.currentWeight;
	inventoryMaxWeight: Signal<number> = this.inventoryStore.inventory.maxWeight;

	armor: Signal<ArmorDetail> = this.inventoryStore.armor;
	primaryWeapon: Signal<WeaponDetail> = this.inventoryStore.primaryWeapon;
	secondaryWeapon: Signal<WeaponDetail> = this.inventoryStore.secondaryWeapon;

	// Menu contextuel changement de mode d'arme
	readonly contextMenuPosition = signal<{ x: number; y: number } | null>(null);
	readonly contextMenuSlot = signal<EquippedSlot | null>(null);
	readonly showContextMenu = signal(false);

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
		this.inventoryStore.changeWeaponMode({ mode, equippedSlot });
	}

	selectItem(item: ItemInstance | null) {
		if (!item) return;
		this.inventoryStore.selectItem(item);
	}

	unequipItem(itemInstance: (ArmorInstance | WeaponInstance)) {
		this.showContextMenu.set(false);
		this.inventoryStore.unequipItem({ itemInstance });
	}

	unloadWeapon(weaponInstance: WeaponInstance) {
		this.showContextMenu.set(false);
		this.inventoryStore.unloadWeapon({ weaponInstance });
	}

}
