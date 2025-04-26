import { Component, computed, inject, Signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { LanguageService } from '@core/services/language.service';
import { WebSocketService } from '@core/services/websocket-service';
import { Character } from '@features/game/models/character.model';
import { CharacterStore } from '@features/game/stores/character.store';
import { MapStore } from '@features/game/stores/map.store';
import { TranslateModule } from '@ngx-translate/core';

@Component({
	selector: 'app-game-sidebar',
	imports: [
		TranslateModule,
		MatCardModule,
		MatProgressBarModule
	],
	templateUrl: './game-sidebar.component.html',
	styleUrls: ['./game-sidebar.component.scss']
})
export class GameSidebarComponent {
	private webSocketService = inject(WebSocketService);
	private languageService = inject(LanguageService);

	private readonly mapStore = inject(MapStore);
	private readonly characterStore = inject(CharacterStore);

	readonly character: Signal<Character | null> = this.characterStore.character;
	readonly terrainDescription = this.mapStore.terrainConfigMap;
	readonly currentTile = computed(() => this.characterStore.currentTile() ?? null);

	protected currentTerrainConfig = computed(() => {
		const tile = this.currentTile();
		if (!tile) return null;
		return this.terrainDescription()[tile.type.toLowerCase()];
	});

	protected currentLanguage = computed(() => this.languageService.currentLanguage());
	private events = this.webSocketService.gameEvents;
	protected eventList = computed(() => this.events());

}
