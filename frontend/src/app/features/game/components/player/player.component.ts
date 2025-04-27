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
import { ItemType } from '@features/game/models/inventory/inventory.model';
import { InventoryStore } from '@features/game/stores/inventory.store';
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslateModule } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
	selector: 'app-inventory',
	templateUrl: './player.component.html',
	styleUrl: './player.component.scss',
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
		MatToolbarModule
	]
})
export class PlayerComponent {
	private readonly playerStore = inject(PlayerStore);
	private readonly dialogRef = inject(MatDialogRef<PlayerComponent>);
	private readonly inventoryStore = inject(InventoryStore);
	private readonly languageService = inject(LanguageService);

	protected readonly ItemType = ItemType;

	// Stores
	readonly player = this.playerStore.player();
	readonly inventory = this.inventoryStore.inventory();

	// Computed
	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	@HostListener('window:keydown', ['$event'])
	handleKeyboardEvent(event: KeyboardEvent) {
		if ((event.key === 'c' || event.key === 'C') && !['INPUT', 'TEXTAREA'].includes((event.target as HTMLElement).tagName)) {
			event.preventDefault();
			this.close();
		}
	}

	close() {
		this.dialogRef.close();
	}
}
