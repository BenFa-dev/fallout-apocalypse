import { Component, computed, inject, input, output, Signal } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { LanguageService } from '@core/services/language.service';
import {
	AmmoInstance,
	ArmorDetail,
	ArmorInstance,
	EquippedSlot,
	ItemInstance,
	ItemType,
	WeaponDetail,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory-item-context-menu',
	templateUrl: './inventory-item-context-menu.component.html',
	styleUrls: ['./inventory-item-context-menu.component.scss', '../../inventory-context-menu.scss'],
	providers: [AsItemPipe],
	imports: [
		TranslatePipe,
		AsItemInstancePipe,
		MatIcon
	]
})
export class InventoryItemContextMenuComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);

	armor: Signal<ArmorDetail> = this.inventoryStore.armor;
	primaryWeapon: Signal<WeaponDetail> = this.inventoryStore.primaryWeapon;
	secondaryWeapon: Signal<WeaponDetail> = this.inventoryStore.secondaryWeapon;

	// Menu contextuel changement de mode d'arme
	readonly contextMenuPosition = input<{ x: number; y: number } | null>(null);
	readonly contextMenuItemInstance = input<ItemInstance | null>(null);
	readonly currentShowContextMenu = input<boolean>();

	showContextMenu = output<boolean>();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	equipItem(itemInstance: WeaponInstance | ArmorInstance | null, slot: EquippedSlot) {
		if (!itemInstance) return
		this.inventoryStore.equipItem({ itemInstance, targetedSlot: slot });
		this.showContextMenu.emit(false);
	}

	loadWeapon(weaponInstance: WeaponInstance | null, ammoInstance: AmmoInstance | null) {
		if (!weaponInstance || !ammoInstance) return
		this.inventoryStore.loadWeapon({ weaponInstance, ammoInstance });
		this.showContextMenu.emit(false);
	}

}
