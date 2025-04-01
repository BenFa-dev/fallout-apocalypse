import { EnvironmentInjector, inject, runInInjectionContext } from '@angular/core';
import { environment } from '@environments/environment.development';
import { TerrainConfiguration } from '@features/game/models/terrain-configuration.model';
import { GridService } from '@features/game/services/domain/grid.service';
import { MovementService } from '@features/game/services/domain/movement.service';
import { PlayerService } from '@features/game/services/domain/player.service';
import { TooltipService } from '@features/game/services/domain/tooltip.service';
import { MapStore } from '@features/game/stores/map.store';
import { PhaserStore } from '@features/game/stores/phaser.store';
import { Scene } from 'phaser';
import type { Board, default as RexBoardPlugin } from 'phaser3-rex-plugins/plugins/board-plugin';

export function createMainScene(envInjector: EnvironmentInjector): MainScene {
  return runInInjectionContext(envInjector, () => new MainScene());
}

export class MainScene extends Scene {

  public readonly rexBoard!: RexBoardPlugin;

  private readonly phaserStore = inject(PhaserStore);
  private readonly mapStore = inject(MapStore);
  private readonly gridService = inject(GridService);
  private readonly movementService = inject(MovementService);
  private readonly playerService = inject(PlayerService);
  private readonly tooltipService = inject(TooltipService);

  constructor() {
    super({ key: 'MainScene' });
  }

  /** Précharge les assets nécessaires au jeu */
  preload() {
    this.loadTerrainAssets()
    console.log('✅ Assets de terrains chargés avec succès');

    this.load.svg('player', '/assets/textures/player.svg');
    console.log('✅ Asset du joueur chargé avec succès');

  }

  create() {
    console.log('🎮 Initialisation de la scène et des services');
    this.phaserStore.setGame(this, this.getBoard());
    this.tooltipService.createTooltipModel();
    this.playerService.createPlayer();
    this.movementService.setupKeyboardControls();
    this.gridService.createGridTiles();
  }

  /** Charge les assets des terrains */
  private loadTerrainAssets() {
    const terrainConfigs = this.mapStore.terrainConfigurations();
    terrainConfigs.forEach((config: TerrainConfiguration) => {
      const type = config.name.toLowerCase().replace(/_/g, '-');
      this.load.image(type, config.path);
    });
    this.load.image("unknown", "/assets/textures/terrains/unknown.png");
  }

  /** Configure le plateau de jeu avec le plugin RexBoard */
  private getBoard(): Board {
    if (!this.rexBoard) {
      throw new Error('RexBoard plugin not initialized');
    }

    return this.rexBoard.add.board({
      grid: {
        gridType: 'quadGrid',
        x: 0,
        y: 0,
        cellWidth: environment.scene.board.size,
        cellHeight: environment.scene.board.size,
        type: 'orthogonal'
      }
    });
  }

  override update() {
  }
}
