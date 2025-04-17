import { Component, computed, inject, input, output } from '@angular/core';
import { LanguageService } from '@core/services/language.service';
import {
	EquippedSlot,
	ItemDetail,
	ItemInstance,
	ItemType,
	WeaponInstance,
	WeaponMode,
	WeaponModeIcons
} from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemDetailPipe } from '@shared/pipes/as-item-detail.pipe';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-slot-context-menu',
	templateUrl: './inventory-slot-context-menu.component.html',
	styleUrls: ['./inventory-slot-context-menu.component.scss', '../../inventory-context-menu.scss'],
	providers: [AsItemPipe],
	imports: [
		TranslatePipe,
		AsItemInstancePipe,
		AsItemPipe,
		AsItemDetailPipe
	]
})
export class InventorySlotContextMenuComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;
	protected readonly WeaponModeIcons = WeaponModeIcons;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);

	// Menu contextuel changement de mode d'arme
	readonly contextMenuPosition = input<{ x: number; y: number } | null>(null);
	readonly contextMenuSlot = input<EquippedSlot | null>(null);
	readonly contextMenuItemDetail = input<ItemDetail | null>(null);
	readonly currentShowContextMenu = input<boolean>();

	showContextMenu = output<boolean>();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	changeWeaponMode(mode: WeaponMode, equippedSlot: EquippedSlot) {
		this.showContextMenu.emit(false);
		this.inventoryStore.changeWeaponMode({ mode, equippedSlot });
	}

	unequipItem(itemInstance: ItemInstance) {
		this.showContextMenu.emit(false);
		this.inventoryStore.unequipItem({ itemInstance });
	}

	unloadWeapon(weaponInstance: WeaponInstance) {
		this.showContextMenu.emit(false);
		this.inventoryStore.unloadWeapon({ weaponInstance });
	}
}
