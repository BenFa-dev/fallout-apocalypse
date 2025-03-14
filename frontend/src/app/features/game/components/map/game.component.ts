import { AfterViewInit, Component, computed, effect, inject, OnDestroy } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { GameSidebarComponent } from '@features/game/components/game-sidebar/game-sidebar.component';
import { CANVAS_ID, StartGame } from '@features/game/components/phaser/game/game.config';
import { GridService } from '@features/game/services/domain/grid.service';
import { MovementService } from '@features/game/services/domain/movement.service';
import { PlayerService } from '@features/game/services/domain/player.service';

import { TerrainService } from '@features/game/services/domain/terrain.service';
import { CharacterStore } from '@features/game/stores/character.store';
import { MapStore } from '@features/game/stores/map.store';
import { UntilDestroy } from '@ngneat/until-destroy';
import { TranslateModule } from '@ngx-translate/core';
import * as Phaser from 'phaser';

/**
 * Exemple de mise en place de Phaser
 * https://github.com/phaserjs/template-angular
 */
@UntilDestroy()
@Component({
  selector: 'app-game',
  imports: [
    GameSidebarComponent,
    MatCardModule,
    MatProgressBarModule,
    MatSelectModule,
    TranslateModule
  ],
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements AfterViewInit, OnDestroy {
  private readonly characterStore = inject(CharacterStore);
  private readonly gridService = inject(GridService);
  private readonly mapStore = inject(MapStore);
  private readonly movementService = inject(MovementService);
  private readonly playerService = inject(PlayerService);
  private readonly terrainService = inject(TerrainService);
  protected readonly CANVAS_ID = CANVAS_ID;

  readonly playerStats = this.characterStore.playerStats;
  readonly terrainDescription = this.mapStore.terrainConfigMap;

  readonly currentTile = computed(() => this.mapStore.currentTile() ?? null);

  private isReady = computed(() => this.mapStore.isInitialized() && this.characterStore.isInitialized());

  private viewReady = false; // Pour s'assurer que la vue est prête avant de lancer Phaser
  private game?: Phaser.Game;

  constructor() {
    // Surveille l'initialisation des stores, mais ne lance Phaser qu'après le rendu de la vue
    effect(() => {
      if (this.isReady() && this.viewReady) {
        console.log('🔄 Stores initialisés après chargement et vue prête, lancement de Phaser');
        this.startGame();
      }
    });
  }

  ngAfterViewInit() {
    this.viewReady = true;

    // Si les stores sont déjà prêts, on lance immédiatement Phaser
    if (this.isReady()) {
      console.log('🎮 Stores déjà prêts, démarrage immédiat de Phaser');
      this.startGame();
    }
  }

  private startGame() {
    console.log('🚀 Lancement du jeu Phaser');
    this.game = StartGame(
      CANVAS_ID,
      this.gridService,
      this.movementService,
      this.playerService,
      this.terrainService,
      this.mapStore
    );
  }

  ngOnDestroy() {
    if (this.game) {
      this.game.destroy(true);
      this.game = undefined;
    }
  }
}
