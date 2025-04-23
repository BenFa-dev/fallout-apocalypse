import { AfterViewInit, Component, computed, effect, EnvironmentInjector, inject, OnDestroy } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';

import { GameSidebarComponent } from '@features/game/components/game-sidebar/game-sidebar.component';
import { CANVAS_ID, StartGame } from '@features/game/components/phaser/game/game.config';
import { CharacterStore } from '@features/game/stores/character.store';
import { MapStore } from '@features/game/stores/map.store';
import { UntilDestroy } from '@ngneat/until-destroy';
import { TranslateModule } from '@ngx-translate/core';
import Phaser from 'phaser';

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
  protected readonly CANVAS_ID = CANVAS_ID;

  private readonly characterStore = inject(CharacterStore);
  private readonly mapStore = inject(MapStore);
  private readonly injector = inject(EnvironmentInjector);

  private readonly isReady = computed(
    () => this.mapStore.isInitialized() && this.characterStore.isInitialized()
  );

  readonly playerStats = this.characterStore.playerStats;
  readonly terrainDescription = this.mapStore.terrainConfigMap;
  readonly currentTile = computed(() => this.characterStore.currentTile() ?? null);

  private game?: Phaser.Game;
  private viewReady = false;

  constructor() {
    // Surveille l'initialisation des stores, mais ne lance Phaser qu'après le rendu de la vue
    effect(() => {
      if (this.isReady() && this.viewReady) {
        console.log('🔄 Stores initialisés après chargement et vue prête, lancement de Phaser');
        this.startGame();
      }
    });
  }

  ngAfterViewInit(): void {
    this.viewReady = true;

    // Si les stores sont déjà prêts, on lance immédiatement Phaser
    if (this.isReady()) {
      console.log('🎮 Stores déjà prêts, démarrage immédiat de Phaser');
      this.startGame();
    }
  }

  private startGame(): void {
    console.log('🚀 Démarrage du jeu Phaser');
    this.game = StartGame(CANVAS_ID, this.injector);
  }

  ngOnDestroy(): void {
    this.game?.destroy(true);
    this.game = undefined;
  }
}
