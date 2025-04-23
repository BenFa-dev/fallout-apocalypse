import { Component, computed, inject } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatCard, MatCardContent, MatCardHeader } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { LanguageService } from '@core/services/language.service';
import { ContextMenuPositionDirective } from '@features/game/directives/clamp-to-viewport.directive';
import {
	EquippedSlot,
	ItemInstance,
	ItemType,
	WeaponInstance,
	WeaponMode,
	WeaponModeIcons
} from '@features/game/models/inventory/inventory.model';
import { ContextMenuStore } from '@features/game/stores/context-menu.store';
import { InventoryStore } from "@features/game/stores/inventory.store";
import { TranslatePipe } from '@ngx-translate/core';
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
		MatCard,
		MatCardHeader,
		MatCardContent,
		MatDivider,
		MatIcon,
		MatIconButton,
		MatTooltip,
		ContextMenuPositionDirective
	]
})
export class InventorySlotContextMenuComponent {
	protected readonly EquippedSlot = EquippedSlot;
	protected readonly ItemType = ItemType;
	protected readonly WeaponModeIcons = WeaponModeIcons;

	private readonly languageService = inject(LanguageService);
	private readonly inventoryStore = inject(InventoryStore);
	private readonly contextMenuStore = inject(ContextMenuStore);

	// Menu contextuel
	readonly itemInstance = this.contextMenuStore.itemInstance;
	readonly isOpen = this.contextMenuStore.isOpen;
	readonly contextType = this.contextMenuStore.contextType;

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	changeWeaponMode(weaponInstance: WeaponInstance | null, mode: WeaponMode) {
		if (!weaponInstance) return;
		this.inventoryStore.changeWeaponMode({ weaponInstance, mode });
		this.onClose();
	}

	unequipItem(itemInstance: ItemInstance) {
		this.inventoryStore.unequipItem({ itemInstance });
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
