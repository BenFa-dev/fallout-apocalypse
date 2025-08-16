import { Component, computed, inject, Signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { LanguageService } from '@core/services/language.service';
import { WebSocketService } from '@core/services/websocket-service';
import { Character, CharacterCurrentStats } from '@features/game/models/character.model';
import { MapStore } from '@features/game/stores/map.store';
import { PlayerStore } from '@features/game/stores/player.store';
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
	private readonly playerStore = inject(PlayerStore);

	readonly character: Signal<Character | null> = this.playerStore.player;
	readonly currentStats: Signal<CharacterCurrentStats | null> = this.playerStore.currentStats;
	readonly actionPoints: Signal<number | null> = this.playerStore.actionPoints;

	readonly terrainDescription = this.mapStore.terrainConfigMap;
	readonly currentTile = computed(() => this.playerStore.currentTile() ?? null);

	protected currentTerrainConfig = computed(() => {
		const tile = this.currentTile();
		if (!tile) return null;
		return this.terrainDescription()[tile.type.toLowerCase()];
	});

	protected currentLanguage = computed(() => this.languageService.currentLanguage());
	private events = this.webSocketService.gameEvents;
	protected eventList = computed(() => this.events());

}
