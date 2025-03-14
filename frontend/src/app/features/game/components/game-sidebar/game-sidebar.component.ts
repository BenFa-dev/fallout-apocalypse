import { Component, computed, inject, input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { LanguageService } from '@core/services/language.service';
import { WebSocketService } from '@core/services/websocket-service';
import { PlayerStats } from '@features/game/models/player.model';
import { TerrainConfigurationMap } from '@features/game/models/terrain-configuration.model';
import { Tile } from '@features/game/models/tile.model';
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

  playerStats = input.required<PlayerStats>();
  currentTile = input<Tile | null>(null);
  terrainDescription = input.required<TerrainConfigurationMap>();

  protected currentTerrainConfig = computed(() => {
    const tile = this.currentTile();
    if (!tile) return null;
    return this.terrainDescription()[tile.type.toLowerCase()];
  });

  protected progressValue = computed(() => (this.playerStats().actionPoints / this.playerStats().maxActionPoints) * 100);
  protected currentLanguage = computed(() => this.languageService.currentLanguage());

  private events = this.webSocketService.gameEvents;
  protected eventList = computed(() => this.events());

}
