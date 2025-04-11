import { Component, computed, HostListener, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { LanguageService } from '@core/services/language.service';
import {
	InventoryCharacterComponent
} from '@features/game/components/inventory/inventory-character/inventory-character.component';
import {
	InventoryDescriptionComponent
} from '@features/game/components/inventory/inventory-description/inventory-description.component';
import { InventoryListComponent } from '@features/game/components/inventory/inventory-list/inventory-list.component';
import { InventoryStatsComponent } from '@features/game/components/inventory/inventory-stats/inventory-stats.component';
import { ItemType } from '@features/game/models/inventory/inventory.model';
import { CharacterStore } from '@features/game/stores/character.store';
import { InventoryStore } from '@features/game/stores/inventory.store';
import { TranslateModule } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory',
	templateUrl: './inventory.component.html',
	styleUrl: './inventory.component.scss',
	providers: [AsItemPipe],
	imports: [
		MatCardModule,
		MatButtonModule,
		MatDividerModule,
		MatDialogModule,
		TranslateModule,
		MatGridListModule,
		MatTooltipModule,
		MatIconModule,
		MatToolbarModule,
		InventoryListComponent,
		InventoryCharacterComponent,
		InventoryStatsComponent,
		InventoryDescriptionComponent
	]
})
export class InventoryComponent {
	private readonly characterStore = inject(CharacterStore);
	private readonly dialogRef = inject(MatDialogRef<InventoryComponent>);
	private readonly inventoryStore = inject(InventoryStore);
	private readonly languageService = inject(LanguageService);

	protected readonly ItemType = ItemType;

	// Stores
	readonly character = this.characterStore.character();
	readonly inventory = this.inventoryStore.inventory();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	@HostListener('window:keydown', ['$event'])
	handleKeyboardEvent(event: KeyboardEvent) {
		if ((event.key === 'i' || event.key === 'I') && !['INPUT', 'TEXTAREA'].includes((event.target as HTMLElement).tagName)) {
			event.preventDefault();
			this.close();
		}
	}

	close() {
		this.dialogRef.close();
	}
}
