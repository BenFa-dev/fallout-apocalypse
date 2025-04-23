import { Component, computed, inject, Signal } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatCard, MatCardContent, MatCardHeader } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { LanguageService } from '@core/services/language.service';
import { ContextMenuPositionDirective } from '@features/game/directives/clamp-to-viewport.directive';
import {
	AmmoInstance,
	ArmorInstance,
	EquippedSlot,
	ItemType,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { ContextMenuStore } from '@features/game/stores/context-menu.store';
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
		MatIcon,
		MatCard,
		MatCardHeader,
		MatIconButton,
		MatTooltip,
		MatCardContent,
		ContextMenuPositionDirective
	]
})
export class InventoryItemContextMenuComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);
	private readonly contextMenuStore = inject(ContextMenuStore);

	armorInstance: Signal<ArmorInstance | null> = this.inventoryStore.armorInstance;
	primaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.primaryWeaponInstance;
	secondaryWeaponInstance: Signal<WeaponInstance | null> = this.inventoryStore.secondaryWeaponInstance;

	// Menu contextuel
	readonly itemInstance = this.contextMenuStore.itemInstance;
	readonly isOpen = this.contextMenuStore.isOpen;
	readonly contextType = this.contextMenuStore.contextType;

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	equipItem(itemInstance: WeaponInstance | ArmorInstance | null, slot: EquippedSlot) {
		if (!itemInstance) return
		this.inventoryStore.equipItem({ itemInstance, targetedSlot: slot });
		this.onClose();
	}

	loadWeapon(weaponInstance: WeaponInstance | null, ammoInstance: AmmoInstance | null) {
		if (!weaponInstance || !ammoInstance) return
		this.inventoryStore.loadWeapon({ weaponInstance, ammoInstance });
		this.onClose();
	}

	unloadWeapon(weaponInstance: WeaponInstance | null) {
		if (!weaponInstance) return;
		this.inventoryStore.unloadWeapon({ weaponInstance });
		this.onClose();
	}

	onClose() {
		this.contextMenuStore.close()
	}
}
